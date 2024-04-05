1. API Documentation: http://localhost:8080/swagger-ui/index.html


2. Sending real emails:
   - Uncomment spring.mail.host=smtp.gmail.com
   - Uncomment spring.mail.port=587
   - Comment spring.mail.host=127.0.0.1
   - Comment spring.mail.port=1025
   

3. Sending emails to MailCatcher:
   - Start MailCatcher docker container https://hub.docker.com/r/dockage/mailcatcher