# HOW-TO Build and Run Transformers Battle API

First clone this project using the following command:
- git clone https://github.com/theonemurilo/transformers

After downloaded, be sure maven is properly installed and configured in your machine.\
If yes, you will be able to execute the command to build and run the application:
- mvn spring-boot:run
 
# API Documentation
The API Documentation was produced using Swagger, so access the following URLs after the application is running:
- http://localhost:8080/v2/api-docs
- http://localhost:8080/swagger-ui.html

# Some notes about the Tech Stack
The project was developed in Kotlin which I'm still getting familiar with. The language is way simpler and it's possible to write a lot more with less and concise code. The extensions for collections and null-ables are very cool and safe to use it, so those are some good reasons to use Kotlin instead of Java.

The tests were written in Spock, because today I haven't seen yet a test framework that can do what it does. The Data tests using those "tables" make the test a very good experience and, visually speaking, anyone can easily understand what is being tested. The labels "given, when, then, expect, where", also are a good way to organize the steps of the test. In certain way, the test is something visualy nice to see and read.

# Assumptions about the test
a) - Survivors: I've considered losing survivors those who win a fight, but his team loses the battle. Also, those skipped are considered survivors as well.\
b) - The "All Destroyed" outcome is just considered when there are Prime or Predaking fighting facing each other. When all battles are tied, all transformers are destroyed, but the flag "All Destroyed" is not used.\
c) - I considered the following priority to evaluate the rules for the battle:
 * 1 - No Fights enough: One of the teams don't have at least one transformer
 * 2 - All Destroyed: Prime or Predaking facing each other

d) When the above battle rules are not matched, then the fights facing each other follow the given priority rules:
 * 1 - Automatically Win-Loss: A Prime or Predaking facing a regular transformer
 * 2 - Win-Loss by Courage and Strength
 * 3 - Win-Loss by Skill
 * 4 - Overall Rating: If all fight rules above don't match, then the overall rating is used to know who wins the fight
