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

Import the request 

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



## References

### Spring Boot TLS

* [Tutorial](https://www.baeldung.com/x-509-authentication-in-spring-security)


