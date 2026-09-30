# Document publishing: learning the State pattern

This repository grows a small Java workflow one commit at a time. Run `bash scripts/test.sh` at any commit. Java 17 and `rg` are required; no build framework or downloads are needed.

At this starting point, a document moves from Draft to In Review to Published. The enum and two checks are simple and appropriate. We will add actions until the rules become harder to maintain, introduce a realistic mistake, and then extract state-specific behavior.

## Step 02: more actions

Editing is allowed only in Draft. A reviewer can reject a document and return it to Draft. Notice that each action now knows which states exist. The checks still work, but state-specific decisions are accumulating inside `Document`.

## Step 03: a change exposes a bug

The new Archived state supports restoring to Draft. The test expects archived documents to reject editing and rejection. This commit intentionally fails: `edit` and `reject` list only the three older states, so their missing branches silently return. Run the test and inspect those methods before continuing.
