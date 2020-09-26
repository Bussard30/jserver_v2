# jserver_v2
Server java application that is designed for handling large-scale custom-protocol tcp data traffic.
Makes most sense to use with CPUs with more threads (>16 Threads, scales more or less linearly).
Might have trouble using less than ~8 Threads efficiently.
Currently work in progress.

Features:
- Asynchronous multithreading job/eventhandling system
- Easy to add packets and protocols
- Logger system
- TLS 1.3

