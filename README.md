# URL Shortener
Web application letting user to create short URL and manage them

* To access dashboard run the following get rest with mock response
~~~
http://localhost:8000/dashboard

[  
   {  
      "shortUrl":"http://10.0.75.1:8000/ee30c62f",
      "longUrl":"https://github.com",
      "numberOfHits":3
   },
   {  
      "shortUrl":"http://10.0.75.1:8000/cac87a2c",
      "longUrl":"https://www.google.com/",
      "numberOfHits":2
   }
]
~~~

* To generate new short url run following POST Request with mock response
~~~
curl --header "Content-Type: application/json" \
  --request POST \
  --data 'https://www.google.com/' \
  http://localhost:8080/generate/
  
{  
   "shortUrl":"http://10.0.75.1:8000/ee30c62f",
   "longUrl":"https://github.com",
   "numberOfHits":0
}
~~~

* The response contains short url