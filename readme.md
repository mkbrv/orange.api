# Orange Cloud API Java Client

Provides a java client for the Orange Cloud API as defined in: https://developer.orange.com/apis

**_-work in progress-_**


## Project structure
**Maven**modules:
 - common
    > Contains common classes and interfaces used in the project.

 - http-client
    > Contains an implementation of the http client interface which uses internally the apache http client.
    It also contains an exception aware client, which parses and throws exceptions.

 - identity
    > Contains the interface and implementation of the Orange Identity API.
    Has the http client as a provided dependency, allowint it to be replaced.

 - cloud
     > Contains the interface and implementation of the Orange Identity Cloud API.
     Has the http client as a provided dependency, allowint it to be replaced.

 - client
    > Contains dependencies to all previous modules, being a simple way of using the entire project.

 - integration-tests
    > Contains integration tests.



## to do:
- [x] Project structure
- [x] HttpClient
- [x] Error parsing
- [x] Identity API
- [ ] Orange Cloud API
    - [x] Freespace
    - [ ] **Orange Folders**
    - [ ] Orange Files
    - [ ] Upload
- [ ] Integration testing


___

##### Strange Orange issues:
>WARNING: Cookie rejected [proxyIn_com="mspvp400", version:0, domain:openid.orange.fr, path:/, expiry:Fri Feb 19 23:06:55 CET 2016] Illegal 'domain' attribute "openid.orange.fr". Domain of origin: "api.orange.com"
