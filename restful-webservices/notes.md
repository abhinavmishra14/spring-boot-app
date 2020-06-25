
# RESTful Web Services

##### Included mvnw/mvnw.cmd
- This allows you to run the Maven project without having Maven installed and present on the path. It downloads the correct Maven version if it's not found (as far as I know by default in your user home directory). The  mvnw file is for Linux (bash) and the mvnw.cmd is for the Windows environment.

- To create or update all necessary Maven Wrapper files execute the following command:

       `mvn -N io.takari:maven:wrapper`

- To use a different version of maven you can specify the version as follows:

       `mvn -N io.takari:maven:wrapper -Dmaven=3.3.3`


- Both commands require maven on PATH (add the path to maven bin to Path on System Variables) if you already have mvnw in your project you can use ./mvnw instead of mvn in the commands.

## Social Media Application Resource Mappings

### User -> Posts

- Retrieve all Users      - GET  /users
- Create a User           - POST /users
- Retrieve one User       - GET  /users/{id} -> /users/1   
- Delete a User           - DELETE /users/{id} -> /users/1

- Retrieve all posts for a User - GET /users/{id}/posts 
- Create a posts for a User - POST /users/{id}/posts
- Retrieve details of a post - GET /users/{id}/posts/{postId}

## Error in the Log
```
Resolved exception caused by Handler execution: 
org.springframework.http.converter.HttpMessageNotWritableException: 
No converter found for return value of type: 
class com.github.abhinavmishra14.rws.model.Response, com.github.abhinavmishra14.rws.model.User, com.github.abhinavmishra14.rws.model.Post
```
- This happened because there were no getters in HelloWorldBean class

## Questions to Answer

- What is dispatcher servlet? - It is the front controller for spring web mvc framework
- Who is configuring dispatcher servlet? - Spring boot auto configuration is configuring the dispatcher servlet
- What does dispatcher servlet do? 
- How does the HelloWorldBean object get converted to JSON? - Spring boot auto configuration is configuring which is configuring http message converters and jackson object to message beans
- Who is configuring the error mapping? - Spring boot auto configuration is configuring default error mapping/error page which can be customized as well.

- Mapping servlet: 'dispatcherServlet' to [/]

- Mapped "{[/error]}" onto 
public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
- Mapped "{[/error],produces=[text/html]}" onto 
public org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)

### Example Requests

#### GET http://localhost:8080/rwsspringboot/users
```json
[
    {
        "id": 1,
        "name": "Abhinav",
        "birthdate": "1986-07-19T04:40:20.796+0000"
    },
    {
        "id": 2,
        "name": "Abhishek",
        "birthdate": "1987-07-19T04:40:20.796+0000"
    },
    {
        "id": 3,
        "name": "Ashu",
        "birthdate": "1988-07-19T04:40:20.796+0000"
    }
]
```
#### GET http://localhost:8080/rwsspringboot/users/1
```json
{
    "id": 1,
    "name": "Abhinav",
    "birthdate": "2017-07-19T04:40:20.796+0000"
}
```
#### POST http://localhost:8080/rwsspringboot/users
```json array
	{
	  "name": "sunny2",
	  "birthdate": "1992-02-25"
	}
```

#### GET http://localhost:8080/rwsspringboot/users/5
- Get request to a non existing resource. 
- The response shows default error message structure auto configured by Spring Boot.

```json
{
    "timestamp": "2020-06-06T05:28:37.534+0000",
    "status": 404,
    "error": "Not Found",
    "trace": ""com.github.abhinavmishra14.rws.exceptions.UserNotFoundException: User with id '5' not found! com.github.abhinavmishra14.rws.controller.UserRestController.deleteUser(UserRestController.java:129)...."
    "message": "User with id '5' not found!",
    "path": "/users/5"
}
```

#### GET http://localhost:8080/rwsspringboot/users/0
- Get request to a invalid resource. 
- The response shows a Customized Message Structure, generated using com.github.abhinavmishra14.rws.exceptions.CustomResponseEntityExceptionHandler

```json
{
    "message": "User id '0' is invalid!",
    "details": "uri=/rwsspringboot/users/0",
    "timestamp": "2020-06-06T04:09:25.112+00:00"
}
```

#### POST http://localhost:8080/rwsspringboot/users with Validation Errors

##### Request
```json
{
    "name": "a",
    "birthdate": "2000-07-19T04:29:24.054+0000"
}
```
##### Response - 400 Bad Request
```json
{
    "timestamp": "2017-07-19T09:00:27.912+0000",
    "message": "Validation Failed",
    "details": "org.springframework.validation.BeanPropertyBindingResult: 1 errors\nField error in object 'user' on field 'name': rejected value [R]; codes [Size.user.name,Size.name,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [user.name,name]; arguments []; default message [name],2147483647,2]; default message [Name should have atleast 2 characters]"
}
```
### HATEOAS -- Hypermedia as the engine of application state 
#### GET http://localhost:8080/rwsspringboot/users/1 with HATEOAS
```json
{
	"id": 1,
	"name": "abhinav",
	"birthdate": "1987-03-15T04:30:27.889+00:00",
	"_links": {
		"all-users": {
			"href": "http://127.0.0.1:8181/rwsspringboot/users"
		}
	}
}
```
### Internationalization

##### Configuration 
- LocaleResolver
   - Default Locale - Locale.US
- ResourceBundleMessageSource

##### Usage via old approach where we use SessionLocaleResolver
- Update the spring boot app class to add following methods.

 ```java
  @Bean
  public LocaleResolver localeResolver() {
  	SessionLocaleResolver localeResolver = new SessionLocaleResolver();
  	localeResolver.setDefaultLocale(Locale.US);
  	return localeResolver;
  }
  
  @Bean //Be careful about the method name, it should be messageSource()
  public ResourceBundleMessageSource messageSource() {
  	ResourceBundleMessageSource msgSrc = new ResourceBundleMessageSource();
  	msgSrc.setBaseName("messages");
  	return msgSrc;
  }
 ```
- Autowire MessageSource in Controller class 

   ```java
   @Autowired
   private MessageSource messageSource;
  ```
- User param as `"@RequestHeader(value = "Accept-Language", required = false) Locale locale"` in the controller method
- Use `messageSource.getMessage("helloWorld.message", null, locale)` to set the response message.

##### Usage via new approach where we use AcceptHeaderLocaleResolver
- Update the spring boot app class to add following methods.

 ```java
  @Bean
  public LocaleResolver localeResolver() {
  	AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
  	localeResolver.setDefaultLocale(Locale.US);
  	return localeResolver;
  }
 ```
- ``ResourceBundleMessageSource messageSource()`` is no longer needed, we can set the messages base file name in ``application.properties``
  e.g.: `spring.messages.basename=messages`
  
- Autowire MessageSource in Controller class

 ```java
  @Autowired
  private MessageSource messageSource;
 ```
- Use `messageSource.getMessage("helloWorld.message", null, LocaleContextHolder.getLocale())` to set the response message.

### XML Representation of Resources

#### GET http://localhost:8080/rwsspringboot/users
- Accept application/xml

```xml
<List>
    <item>
        <id>2</id>
        <name>Eve</name>
        <birthDate>2017-07-19T10:25:20.450+0000</birthDate>
    </item>
    <item>
        <id>3</id>
        <name>Jack</name>
        <birthDate>2017-07-19T10:25:20.450+0000</birthDate>
    </item>
    <item>
        <id>4</id>
        <name>Ranga</name>
        <birthDate>2017-07-19T10:25:20.450+0000</birthDate>
    </item>
</List>
```

#### POST http://localhost:8080/rwsspringboot/users
- Accept : application/xml
- Content-Type : application/xml

Request

```xml
<item>
        <name>Ranga</name>
        <birthDate>2017-07-19T10:25:20.450+0000</birthDate>
</item>
```

Response
- Status - 201 Created

## Generating Swagger Documentation


```java
	public static final Contact DEFAULT_CONTACT = new Contact(
			"Ranga Karanam", "http://www.in28minutes.com", "in28minutes@gmail.com");
	
	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
			"Awesome API Title", "Awesome API Description", "1.0",
			"urn:tos", DEFAULT_CONTACT, 
			"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");

	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = 
			new HashSet<String>(Arrays.asList("application/json",
					"application/xml"));

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT_API_INFO)
				.produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.consumes(DEFAULT_PRODUCES_AND_CONSUMES);
	}

```

### Resource Method description
```java
	@GetMapping("/users/{id}")
	@ApiOperation(value = "Finds Users by id",
    notes = "Also returns a link to retrieve all users with rel - all-users")
	public Resource<User> retrieveUser(@PathVariable int id) {
```

### API Model
```java

@ApiModel(value="User Details", description="Contains all details of a user")
public class User {

	@Size(min=2, message="Name should have atleast 2 characters")
	@ApiModelProperty(notes = "Name should have atleast 2 characters")
	private String name;

	@Past
	@ApiModelProperty(notes = "Birth Date should be in the Past")
	private Date birthDate;
```

#### Filtering

##### Code
```java
@JsonIgnoreProperties(value={"field1"})
public class SomeBean {
	
	private String field1;
	
	@JsonIgnore
	private String field2;
	
	private String field3;

```
##### Response
```json
{
    "field3": "value3"
}
```

### Versioning
 - Media type versioning (a.k.a “content negotiation” or “accept header”)
   - GitHub
 - (Custom) headers versioning
   - Microsoft
 - URI Versioning
   - Twitter
 - Request Parameter versioning 
   - Amazon
 - Factors
  - URI Pollution
  - Misuse of HTTP Headers
  - Caching
  - Can we execute the request on the browser?
  - API Documentation
 - No Perfect Solution 

#### More
- https://www.mnot.net/blog/2011/10/25/web_api_versioning_smackdown
- http://urthen.github.io/2013/05/09/ways-to-version-your-api/
- http://stackoverflow.com/questions/389169/best-practices-for-api-versioning
- http://www.lexicalscope.com/blog/2012/03/12/how-are-rest-apis-versioned/
- https://www.3scale.net/2016/06/api-versioning-methods-a-brief-reference/


#### Table Structure

```sql
create table user (
id integer not null, 
birth_date timestamp, 
name varchar(255), 
primary key (id)
);

create table post (
id integer not null, 
description varchar(255), 
user_id integer, 
primary key (id)
);

alter table post 
add constraint post_to_user_foreign_key
foreign key (user_id) references user;
```