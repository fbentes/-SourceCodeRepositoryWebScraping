# IMDbQueryProject
## Java solution with Spring Boot to scraping html repository in GitHub.
### Development environment 

- **Windows 10 Home**
- [**Spring Tools 4 for Eclipse Download**](https://download.springsource.com/release/STS4/4.8.0.RELEASE/dist/e4.17/spring-tool-suite-4-4.8.0.RELEASE-e4.17.0-win32.win32.x86_64.self-extracting.jar)
- [**RedHat Java OpenJDK 8 Download**](https://developers.redhat.com/download-manager/file/java-1.8.0-openjdk-1.8.0.265-3.b01.redhat.windows.x86_64.msi) (faça o download apenas se você não tiver, **_no mínimo_**, um JDK 8 instalado, seja da RedHat, Oracle ou outro fornecedor).

Example to test:

To test response respository request (GET VERB):
https://sourcecodereposwebscraping.herokuapp.com/fetchDataRepository?repositoryUrl=https://github.com/fbentes/EmitesJavaJobApplicationChallenge

https://sourcecodereposwebscraping.herokuapp.com/fetchDataRepository?repositoryUrl=https://github.com/fbentes/SourceCodeRepositoryWebScraping

https://sourcecodereposwebscraping.herokuapp.com/fetchDataRepository?repositoryUrl=https://github.com/fbentes/FrameworkWebDesk

https://sourcecodereposwebscraping.herokuapp.com/fetchDataRepository?repositoryUrl=https://github.com/fbentes/microtef-hire-me

To clear all repositorires caches (POST VERB):
https://sourcecodereposwebscraping.herokuapp.com/clearAllCache

To clear a especific repository (POST VERB):
https://sourcecodereposwebscraping.herokuapp.com/clearCache?repositoryUrl=https://github.com/fbentes/EmitesJavaJobApplicationChallenge/tree/master/IMDbQueryProject

Only GitHub repository is implmented, but the implmentation is flexible to any others using Strategy design patterns !

