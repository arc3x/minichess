#minichess

##credits
Framework - Simon Niklaus

Client - Matthew Slocum

##overview
the repository contains the framework itself, as well as a client. the framework provides a generic scaffolding, while a fully implemented client represents an artificial chess player. the framework as well as a client therefore need to be executed concurrently in order to have a running system.

the framework provides a graphical user interface that is accessible through a webbrowser, as well as several test cases to validate the implementation of the client. to let artificial chess players compete against each other, the framework furthermore provides a network interoperability through an external server.

<p align="center"><img src="http://content.coderect.com/ChessRect/Teaching/ScreenshotThumb.png" alt="ScreenshotThumb"></p>

##prerequisites
while you are able to choose between different languages to implement the client, the framework itself is written in c. therefore, the `gcc` compiler must be present and running in a `64 bit` environment to build the framework. you might have to separately install the appropriate compiler toolchain. should you be able to use `apt-get`, you can install the following package to do so.

```
sudo apt-get install build-essential
```

besides the compiler toolchain, it is necessary to install `zeromq` with version `3.*` which will be used to communicate between the framework and a client.

```
sudo apt-get install libzmq3-dev
```

##usage
as stated in the overview, it is necessary to use the framework together with a client. this section will accordingly outline the necessary steps for compiling and running the framework as well as the client.

###`framework`
a rudimentary makefile is already provided in order to build the framework. after you open a console and navigate to the folder of the framework, you can therefore utilize the `make` command.

```
make
```

after a successful compilation, a `framework` binary should have been built that you can subsequently execute to run the framework.

```
./framework
```

once the framework has started, it is possible to access the corresponding webinterface through your webbrowser. to do so, simply navigate to the url that is stated below. note that you will initially only see a message that there is no client present, which will disappear once a client is being executed concurrently.

```
localhost:8042
```

###`client-java`
a rudimentary makefile is already provided in order to build the client. after you open a console and navigate to the folder of the client, you can therefore utilize the `make` command.

```
make
```

after a successful compilation, a `client` jar should have been built that you can subsequently execute to run the client.

```
java -ea -jar client.jar
```

once the client is started, it should automatically connect to the framework. of course, a the framework needs to be executed concurrently to allow this interconnection to happen. after the client as well as the framework are being executed cooperatively, you should be able to access the webinterface and to interact with the client through the said graphical user interface.

##test cases
note that the more advanced test cases rely on the basic test cases to succeed. for this reason, the most advanced test case `test_moveAlphabeta` does succeed even with the provided empty clients. other test cases like `test_moveNegamax` might not halt without a correct implementation of the basic functions. the grading will therefore not only evaluate newly implemented features, but furthermore take the basic functionalities into consideration as well.

##remote interface
in order to be able to access the webinterface of the framework on a remote machine, you need to establish an ssh tunnel such that you can use the browser on your local computer. there a few online resources that describe how this can be done and it eventually boils down to the following command. note that there is an equivalent way when using putty.

```
ssh <username>@<machine>.cs.pdx.edu -L 8080:localhost:8080
```

you will eventually need three ssh connections in parallel. one for the execution of the framework, one for the execution of the client as well as one for the tunnel in order to be able to access the webinterface.

##alternative workarounds
using a virtual machine is always a viable option. i personally do this as well and developed this framework in a debian environment that is running within a virtual machine. note that there are quite a few free virtualizers to choose from and while i have a preferred one, i would like to take the liberty of not making any advertisements here. i would therefore recommend to read a few related online resources.

should you only be having trouble building the framework, you can try the corresponding binary within the binaries folder of this repository. note however, that even a binary might not work in case some dependencies are not met. there so far is furthermore only one prebuilt binary for linux.

##frequently asked questions
*why does the framework segfault when it starts?* this can happen when the port it tries for the communication with the client is invalid or in use by another instance of the framework. to solve this issue, make sure that a valid port has been chosen and that there is no other running instance of the framework. make sure to read the previous section about the linux lab, should you be using this resource to work on your artificial chess player.

*there is a message from zeromq, stating that the address is already in use?* there might be another instance of the framework or the client running in the background, that prevents a newly executed framework or client from using the same port. it is therefore necessary to close the instance that has accidentally been left open. should it not be the case that there has been an instance left open, the operating system might not yet have gained back the control over a port that has previously been in use. in this case, it is recommended to retry it a few times.

*the system does not respond anymore, how can i resolve this issue?* there are many potential causes for such a behavior. while the framework has been tested extensively, it might still contain an error that causes such an issue. it is therefore recommended to restart the framework as well as the client. it is furthermore likely that the implementation of the player has an issue, so please make sure that the error resides within the framework before opening a case in the issue tracker.

*is my implementation correct if all the test cases work?* while it mathematically might be possible to prove that a certain implementation meets the specification, test cases are far from providing such a guarantee. an implementation of a player might therefore pass all the test cases while still not being able to play chess competitively.

*how meaningful are the benchmarks of the test cases?* there is a significant overhead due to the communication with the client. the test cases need to communicate quite frequently with the client, which is why the influence of this overhead is very noticeable. there is no need for an extensive communication while playing a game against another player however, which is why the communication induced overhead is usually not an issue.

##dependencies
since the framework consists of several components and each component has individual dependencies, they are being listed separately.

###`framework`
* `zeromq`
* `cjson`
* `mongoose`

###`client-java`
* `jeromq`
* `json`


##license
please refer to the appropriate file within this repository.
