# Client FSM

The client is modelled as a finite state machine.

In an initial state the client polls for work.
Once there is work, execution starts.
After that results are uploaded.
Then the client tries to fetch more work.

If the client has an internal error, no more work can be found or the test fails a dirty state is entered and the client requests a reset.
