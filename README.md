# Sample stack for test automation
#### Importing the stack
stk import stack https://github.com/stack-spot/igor_java_gradle_stack
#### Creating a simple Java-Gradle-Selenium project
stk create app app-java-gradle --stackfile stack-java-gradle/stackfile-java-gradle-selenium
#### Running the project
cd app-java-gradle  
Unix: ./gradlew build  
Windows: gradlew.bat build  
  
ps: use the flag `-x test` to build without running the tests