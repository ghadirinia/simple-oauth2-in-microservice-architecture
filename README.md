Hello everyone,

I'd like to share a GitHub link where I've developed a straightforward microservice architecture project featuring OAuth2 for authorization. In this project, you'll find an authorization server named "auth-service," along with "card-service" and "clientinfo-service" serving as resource servers. Notably, the "clientinfo-service" functions as a client of the authorization server, making calls to the "card-service".

The implementation of the service-to-service connection leverages WebFlux, RestTemplate, and Feign, ensuring seamless communication while passing authorization information between them.

Moreover, the API Gateway employs the authorization-code grant type, while the "clientinfo-service" utilizes client-credentials to establish a connection with the authorization server. :)

Feel free to explore the project on GitHub. If you have any questions or feedback, I'd be happy to discuss further.
