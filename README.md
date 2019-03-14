## Overview

Simple proof of concept for mutual auth to OAuth2 micro service


## Steps

Setup steps used to create the project and crypto resources

### Keystore

#### Certification Authority

Using [easyrsa](https://github.com/OpenVPN/easy-rsa.git)

Setting up the CA

``` 
mkdir crypto/certauth
cd crypto/certauth

easyrsa init-pki
easyrsa build-ca
```

Using `matooa-ca` as the CA name 


#### Server Key and Certificate

Create the key (use "password" for the password)

```
keytool -genkey -alias matooa -keyalg RSA -storepass password -keystore keystore/keystore.jks
```

Create certificate request from key

```
keytool -certreq -keystore keystore/keystore.jks -alias matooa -file matooa-server.req 
```

Import the request under the certification authority directory

``` 
easyrsa import-req ../../matooa-server.req matooa
```

Sign the request 

```
easyrsa sign-req server matooa
```

Import CA cert into key store

```
keytool -importcert -alias matooa-ca -keystore keystore/keystore.jks -trustcacerts -file crypto/certauth/pki/ca.crt 
```

Import the issued certificate 

```
keytool -importcert -alias matooa -keystore keystore/keystore.jks -file crypto/certauth/pki/issued/matooa.crt 
```

#### Mutual Auth Certificates

Following the [tutorial](https://www.baeldung.com/x-509-authentication-in-spring-security)

Adding the CA certificate to the trust store

```
keytool -importcert -alias matooa-ca -keystore keystore/truststore.jks -trustcacerts -file crypto/certauth/pki/ca.crt 
```


#### Create Client Crypto 

Using [easyrsa](https://github.com/OpenVPN/easy-rsa.git) again 

Setting up the client 

``` 
mkdir crypto/client
cd crypto/client

easyrsa init-pki
```


#### Create Client Cert 

From the client directory

```
easyrsa gen-req matooa-client 
```

From the CA directory import the client request 

```
easyrsa import-req ../client/pki/reqs/matooa-client.req matooa-client
```

Sign the request

```
easyrsa sign-req client matooa-client
```


#### Curl Client Request 

Run the following curl command to send requests to the server using curl 

```
curl -vvv -k \
--cacert crypto/certauth/pki/ca.crt \
--key crypto/client/pki/private/matooa-client.key \
--cert crypto/certauth/pki/issued/matooa-client.crt \
-X POST -d "HelloYo" -H "Content-Type: text/plain" https://localhost:8443/post 
```

Curl asks for the PEM password (for the key), enter this and it should successfully connect to the server and 
get a response.

## References

### Spring Boot TLS

* [Tutorial](https://www.baeldung.com/x-509-authentication-in-spring-security)


