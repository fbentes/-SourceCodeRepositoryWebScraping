# SourceCodeRepositoryWebScraping Project
## Java solution with Spring Boot: Data retrieved from GitHub website by using web scraping techniques.
### Development environment 

- **Windows 10 Home**
- [**Spring Tools 4 for Eclipse Download**](https://download.springsource.com/release/STS4/4.8.0.RELEASE/dist/e4.17/spring-tool-suite-4-4.8.0.RELEASE-e4.17.0-win32.win32.x86_64.self-extracting.jar)
- [**RedHat Java OpenJDK 8 Download**](https://developers.redhat.com/download-manager/file/java-1.8.0-openjdk-1.8.0.265-3.b01.redhat.windows.x86_64.msi) (download only if you do not have, **_at least_**, a JDK 8 installed, either from RedHat, Oracle or another vendor.).

### Technical documentation:

[SourceCodeRepositoryWebScraping Lucidchart Package Diagram](https://app.lucidchart.com/lucidchart/51efb0ec-ce7f-4f24-bda9-2799064d1543/view?page=0_0#?folder_id=home&browser=icon)

> Note for analysis of sources:
> The classes responsible for the request Client Socket and response Server Socket are, respectively, ** com.imdb.query.client.impl.IMDbClientSocketImpl ** and ** com.imdb.query.server.impl.IMDbClientHandler **.

[SourceCodeRepositoryWebScraping Javadoc](https://github.com/fbentes/SourceCodeRepositoryWebScraping/tree/main/doc/com/sourcecoderepositorywebscraping)

**Note: Only to the GitHub repository was implmented, but the implmentation is flexible and extensible using Strategy design patterns !**

### Example to test the solution:

**From Heroku:**

Call all repositoriries to return lines and bytes of filaes by extension (**GET verb**):

https://sourcecodereposwebscraping.herokuapp.com/fetchDataRepository?repositoryUrl=https://github.com/fbentes/EmitesJavaJobApplicationChallenge

https://sourcecodereposwebscraping.herokuapp.com/fetchDataRepository?repositoryUrl=https://github.com/fbentes/SourceCodeRepositoryWebScraping

https://sourcecodereposwebscraping.herokuapp.com/fetchDataRepository?repositoryUrl=https://github.com/fbentes/FrameworkWebDesk

To clear all repositorires caches (**POST verb**):
https://sourcecodereposwebscraping.herokuapp.com/clearAllCache

To clear a especific repository (**POST verb**):
https://sourcecodereposwebscraping.herokuapp.com/clearCache?repositoryUrl=https://github.com/fbentes/EmitesJavaJobApplicationChallenge/tree/master/IMDbQueryProject

Note: Heroku has limitations of requests by free plan, and because that, it block application. 

**From localhost:**

Call all repositoriries to return lines and bytes of filaes by extension (**GET verb**):

http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/EmitesJavaJobApplicationChallenge

http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/SourceCodeRepositoryWebScraping

http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/FrameworkWebDesk

To clear all repositorires caches (**POST verb**):
https://localhost:9091/clearAllCache

To clear a especific repository (**POST verb**):
https://localhost:9091/clearCache?repositoryUrl=https://github.com/fbentes/EmitesJavaJobApplicationChallenge/tree/master/IMDbQueryProject

**Note: Heroku has limitations of requests on free plan, and because that it block application when multiple requests is sended even in long time. On localhost works fine with multiples requests providing cache by repository argument, concurrently !**
