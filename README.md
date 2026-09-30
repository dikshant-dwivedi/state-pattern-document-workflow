# Document publishing: learning the State pattern

This repository grows a small Java workflow one commit at a time. Run `bash scripts/test.sh` at any commit. Java 17 and `rg` are required; no build framework or downloads are needed.

At this starting point, a document moves from Draft to In Review to Published. The enum and two checks are simple and appropriate. We will add actions until the rules become harder to maintain, introduce a realistic mistake, and then extract state-specific behavior.
