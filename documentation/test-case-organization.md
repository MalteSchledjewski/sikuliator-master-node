# Test case organization

The test cases must be organized across across different versions and operating systems.

Applications should adhere to the operating system's human interface guideline to better integrate into the ecosystems.
Therefore the graphical interface of the application under test will probably be different across operating systems.

Different graphical interfaces are a problem for SikuliX which is based on reference images.
Also simple sequences like starting an application differ across operating systems.


 - Project
   - Flavor

Projects are the enclosing scope for tests.
Tests are not shared across projects.

Flavors are a collection of tests.
Tests are organized in a tree structure inside each flavor.
Tests can be tagged for better searchability.

Each test may have different versions.
Test versions are organized as a directed acyclic tree.
Each test version knows its predecessor.
If a new test version is created, all flavors containing the direct success get notified and may change to the new version.
