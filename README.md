# Prime-Number-Generator-SpringBoot-Pub-Sub-Queue
Generates prime number for a user configured parameter using a pub/sub queue in a springboot application

****To run the application on localhost****
Use Intellij Ultimate (Community edition won't work) or any IDE of your choice that has Java 17 and maven.

Install the windows/mac installer for erlang (25.1 version only) to be able to run rabbitmq (if you don't have erlang already installed) - https://www.erlang.org/patches/otp-25.1

Erlang must be installed using administrative account. Set ERLANG_HOME in system variables to the erlang file path 

Once erlang is installed successfully and running, download rabbitmq - https://www.rabbitmq.com/install-windows.html#installer

I recommend using installer way to download rabbitmq. Aternatively, chocolatey can also be used.

Once rabbitmq is installed successfully, start the rabbitmq service in terminal- rabbitmq-service.bat start

Once rabbitmq is running, open this url in the browser to run the GUI interface to monitor the queue - http://rabbitmq:15672/

The default username and password is 'guest' for rabbitmq.

Once authenticated, the rabbitmq message broker is running successfully.

Open the project in intellij. 

Set up the run configuration by adding the class with the main method.

Run the application.

Go to http://localhost:8080 OR http://localhost:8080/index.html to be able to access the application

Username and password is 'admin' to log into application.

Alternatively, the endpoint can also be monitored in postman.

Enjoy the prime number generation game!

****To run the application on cloud endpoint****

Use this ip address to access the application in your browser - http://35.197.164.42/.

Username and password is 'admin' to log into application.

Application deployed using Docker container and Google Kubernetes Engine.

Enjoy the prime number generation game!

** video also available to watch on: https://clipchamp.com/watch/hTlaEdcyMSI
