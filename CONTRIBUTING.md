# Contribute, and help us improve
We work out of a discord server, feel free to come join us! 
<a href="https://discord.gg/hqXpWSc"><img src="https://img.shields.io/badge/Discord-Join-blue"/></a>

### Help improve Elytra!
Thanks for your interest in the project! Pull Requests welcome for any level of improvement, from a small typo to a new feature, help us make the project better.

### Committing to an Elytra repo
We use this [commit convetion](https://www.conventionalcommits.org/en/v1.0.0/) and a this [branch flow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow), at the end of the day we want a clean commit history, so it would be advisable to respect that.

### Know-hows

##### Create a fork 
In order to send pull requests (PRs), at the top of the project name on the left side you will have the button below described:

<img src="https://i.imgur.com/3O1wI0m.png"/>
<br> Click on the fork icon and you are **ready to go!**

##### Compile the project
Elytra is written in Kotlin and utilizes Gradle 2 as build management tool. To compile it from the source, perform the following steps:
  1. Make sure to have Git and Gradle 2 installed
  2. Clone the repository with `git clone <fork url here>`
  3. Build the project with `gradle clean shadowJar build`
