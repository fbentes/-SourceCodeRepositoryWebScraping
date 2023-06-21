
# SourceCodeRepositoryWebScraping Project


## Requisito: 
O componente recebe uma url de um repositório Git e retorna um Json totalizando as linhas e bytes de todos os arquivos, agrupando por extensão de arquivo. Milhares ou milhões de requisições simultâneas devem ser atendidas sem disparo do erro HTTP 429 – Too Many Request e sem timeout. Todas as requisições devem ser atendidas, independentemente do tempo inicial de requisição, mas as requisições subsequentes devem responder em tempo imediato.

## Solução: 
Através de uma REST API pulicada no Heroku, cada URL requisitada usa uma Thread única e as demais requisições para a mesma URL aguardam até que a primeira requisição desbloqueie após o cache ser preenchido. Assim, se 100 requisições forem feitas, a primeira fará o parsing nos arquivos da URL, demorará o tempo necessário e as 99 requisições subsequentes buscam do cache de imediato. Se for disparado HTTP 429, o componente fica num delay com 3 tentativas até que o servidor não “pense” que há uma invasão indevida. Se mesmo assim não resolver, aí o usuário terá que tentar num outro momento a requisição.



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

**Note: Heroku has limitations of requests on free plan, and because that it block application when multiple requests is sended even in long time. On localhost works fine with multiples requests providing cache by repository argument, concurrently, example:** 

http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/EmitesJavaJobApplicationChallenge  (request 1)
http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/EmitesJavaJobApplicationChallenge  (request 2)
http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/EmitesJavaJobApplicationChallenge  (request 3) 
http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/EmitesJavaJobApplicationChallenge  (request 4) 

**Above, the first request (request 1) lock until finish parsing all files and update your cache while the all others (request 1..12) wait. When your cache is update, request 1 unlock, and requests 2, 3, 4 returns fecthing data from cache of the repository, immediatly to repositoryUrl = https://github.com/fbentes/EmitesJavaJobApplicationChallenge.
As soon as the unlock is done, the next thread lock all others threads to parsing all files to next repository (ex.: https://github.com/fbentes/SourceCodeRepositoryWebScraping). When finish, unlocks all treads and returns to next steps.**

http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/SourceCodeRepositoryWebScraping (request 5)
http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/SourceCodeRepositoryWebScraping (request 6)
http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/SourceCodeRepositoryWebScraping (request 7)
http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/SourceCodeRepositoryWebScraping (request 8)

http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/FrameworkWebDesk (request 9)
http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/FrameworkWebDesk (request 10)
http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/FrameworkWebDesk (request 11)
http://localhost:9091/fetchDataRepository?repositoryUrl=https://github.com/fbentes/FrameworkWebDesk (request 12)

**Only one parsing all files is executed by time to avoid error HTTP 429 and next requests the same repository fecthing by your cache.
If error HTTP 429 is throws, the thread wait sleep of 50s to next attempt read stream for 3 times. Otherwise a error too many requests is shown to client.**
