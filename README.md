# Document publishing: learning the State pattern

For the full derivation, UML diagrams, trade-offs, examples, FAQ, and exercises, read [the post-read](POST_READ.md).

This repository grows a small Java workflow one commit at a time. Run `bash scripts/test.sh` at any commit. Java 17 is required; the historical tags through step 07 also use `rg` in the test script. No build framework or downloads are needed. At the final commit, run `bash scripts/demo.sh` to watch every transition.

At this starting point, a document moves from Draft to In Review to Published. The enum and two checks are simple and appropriate. We will add actions until the rules become harder to maintain, introduce a realistic mistake, and then extract state-specific behavior.

## The rule matrix

| Current state | Allowed action | Next state |
| --- | --- | --- |
| Draft | edit | Draft |
| Draft | submit | In Review |
| In Review | approve | Published |
| In Review | reject | Draft |
| Published | archive | Archived |
| Archived | restore | Draft |

Every other action must throw `IllegalStateException` and leave the document unchanged.

## Follow the commits

```bash
git log --oneline --reverse
git switch --detach 01-small-workflow
bash scripts/test.sh
git switch --detach 03-archived-bug
bash scripts/test.sh       # expected failure: archived edit silently succeeds
git switch --detach before-state-pattern
bash scripts/test.sh
git switch main
bash scripts/test.sh
```

The numbered tags mark teaching steps. `before-state-pattern` is the repaired conditional implementation; `after-state-pattern` is the complete refactor. Compare them with `git diff before-state-pattern after-state-pattern`.

## What changed, and why

**State pattern:** let an object change its behavior when its internal state changes by delegating behavior to a state object. Here `Document` is the *context*, `DocumentState` is the common interface, and `DraftState`, `InReviewState`, `PublishedState`, and `ArchivedState` are concrete states.

- **Composition:** `Document` holds a `DocumentState` object.
- **Polymorphism:** `state.approve(this)` calls the implementation for the current state at runtime.
- **Single Responsibility Principle:** document content and each state's workflow policy have separate homes. The early `Document` accumulated both.
- **Open/Closed Principle:** the early version required edits in several action methods when Archived arrived. The refactor localizes many changes, although a new state can still require changes to transitions and tests. State is not a promise of zero edits.

The pattern trades conditional branches in one class for several small classes and more navigation between files. Its broad interface also exposes actions that most states cannot perform; default methods make these fail safely but can hide an accidentally omitted allowed action. Keep the conditional version when the workflow is small and stable.

## Step 02: more actions

Editing is allowed only in Draft. A reviewer can reject a document and return it to Draft. Notice that each action now knows which states exist. The checks still work, but state-specific decisions are accumulating inside `Document`.

## Step 03: a change exposes a bug

The new Archived state supports restoring to Draft. The test expects archived documents to reject editing and rejection. This commit intentionally fails: `edit` and `reject` list only the three older states, so their missing branches silently return. Run the test and inspect those methods before continuing.

## Step 04: patch the conditionals

Two new branches repair the bug. The test passes again. The important observation is that adding one state forced us to inspect several unrelated methods, because every method contains part of the workflow policy. This is the `before-state-pattern` version.

## Step 05: delegate editing

`Document` now holds a `DocumentState` object. `DraftState` implements the allowed `edit`; the interface's default method rejects editing in the other states. The other actions still use conditionals. This temporary mixed design lets us inspect one delegation before moving the rest.

## Step 06: move review behavior

`submit`, `approve`, and `reject` now delegate too. `DraftState` knows how to submit; `InReviewState` knows how to approve or reject. The interface rejects actions a state does not allow. Follow `Document.approve()` into `InReviewState.approve()` to see runtime polymorphism.

## Step 07: finish the refactor

`PublishedState` handles archiving and `ArchivedState` handles restoring. Every public action in `Document` now delegates to its current state object. The behavior is unchanged from the repaired conditional version; only the organization changed.
