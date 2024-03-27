Techologies used :
Java8 
SpringBoot3
SpringBoot-Security3
JWT
MYSQL database
lambok
Java Mail Sender

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
This is the Springboot JWT Project 
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
For Validating the user credentials we have made our custom Authentication Provider which validates the user. 

When the user is trying to login then ,we are verifying the user authentication by the credentials provider by the user and upon the successfull login we are sending the JWT token in response Header as Authentication and we are also sending the mail to the user upon successfull login using event.

When the user is trying to access any protected resource we are validating the JWT token and if the JWT token is valid, we are permitting the user to access the resources....while validating the token we are checking whether the token received is expired or not...if the token is expired,
we are generating the new token and sending it back to the user in the response header.


