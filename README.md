<h1> JacksonJsonSerializer </h1>

<h2> Pacote de classes que facilitam o processo de serialização utilizando a API Jackson. </h2>

JacksonJsonSerializer é uma implementação da API Jackson que possibilita definir os parâmetros
a serem serializados de um objeto.

* Configuração
** Maven
** Gradle
* Uso

<h2> Configuração </h2>

<h3> Maven </h3>
* Primeiro, adicione o repositorio dentro do seu pom.xml:
```xml
 <repository>
   <id>JacksonJsonSerializer-mvn-repo</id>
   <url>https://raw.github.com/Mateus-A-Soares/JacksonJsonSerializer/mvn-repo/</url>
   <snapshots>
     <enabled>true</enabled>
     <updatePolicy>always</updatePolicy>
   </snapshots>
 </repository>
```
```xml
* Agora importe a dependência:
<dependency>
  <groupId>br.com.lupus</groupId>
  <artifactId>JacksonJsonSerializer</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

<h3> Gradle </h3>
* Primeiro, adicione o repositorio dentro do seu build.gradle:
```gradle
maven {
  name "JacksonJsonSerializer-mvn-repo"
  url "https://raw.github.com/Mateus-A-Soares/JacksonJsonSerializer/mvn-repo/"
}
```

* Agora importe a dependência:
```gradle
compile group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.9.8'
```

<h2> Uso </h2>
* Primeiro crie um modelo que implemente a interface br.com.lupus.Model
```java
public class Usuario implements br.com.lupus.Model {
  
  public Long id;
  public String nome;
  public java.util.Date dataNascimento;
   
  public Long getId() { return id;}
  public void setId(Long id) { this.id = id;}
  
  public Long getNome() { return nome;}
  public void setNome(String nome) { this.nome = nome;}
  
  public java.util.Date getDataNascimento() { return dataNascimento;}
  public void setDataNascimento(java.util.Date dataNascimento) { this.dataNascimento = dataNascimento;}
}
```
* Anote o modelo com a anotação com.fasterxml.jackson.databind.annotation.JsonSerialize, definido o parâmetro using como br.com.lupus.Serializer.class
```java
@JsonSerialize(using = Serializer.class)
```

* Antes de serializar, defina os parâmetros que serão serializados
```java
new Usuario().setAttributes("id", "nome");
```

* Caso queira implementar um serializador específico para variavel, cria uma classe que implemente a interface br.com.FieldJsonSerializer
** Note que o primeiro tipo genérico representa o atributo a ser transformado, enquanto o segundo representa o retorno que será serializado
```java
public class MeuSerializador implements FieldJsonSerializer<Date, String> {

  @Override
  public String serialize(Date date){
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    return sdf.format((Date) value);
  }
}
```

* Por último, anote o atributo com a anotação br.com.lupus.FieldSerializer, definindo a sua implementação da interface como atributo
```java
  @FieldSerializer(serializer = MeuSerializado.class)
	public Date dataNascimento;
```
Pronto! agora sempre que utilizar o método de serialização para JSON da API Jackson, você poderá escolher os atributos que serão ou não serializados através do método Model.setAttributes();
