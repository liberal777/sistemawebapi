package com.rentas.springboot.backend.apirest.auth;

public class JwtConfig {
	//public static final String LLAVE_SECRETA = "alguna.clave.secreta.12345678";
	
	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n"
			+ "MIIEpQIBAAKCAQEApW4MKpdPygBSmvgVn3oWkOkWoTN5myWAgOkWJ7yrB9XjtHZI\r\n"
			+ "XV5bstJfkJIPFdpyzH5WBUDJzXNy+u2MnGNiu5UE8bsjKHLbLBKxMLIz3YOtAgvR\r\n"
			+ "mTEq0sschdB05c2/6ORCrlp3zsd0/0C8/XZwTKZOPifeyz7Sj44WkQwSCyqOMkrB\r\n"
			+ "W2Pp1mwrentHPHWSS1iX2vYWrBLuW6PK1QOTx/HRVC+2OtlnZJrKSPoftyNP7/kU\r\n"
			+ "NWJZXDimbbadWiQWfd6uhTO7AxtZh1lW6Umt2O7ThzhG/cD6w1LfDvdNIW9eocxP\r\n"
			+ "lWWvnCpSVUdKZykOhtduil5xrVzJbEwcTTjTNQIDAQABAoIBAQCNvM8RwtjrEwGz\r\n"
			+ "ozErlXg2Km9oKGldo3EJgiuoWj8TlI/nM5zm2oyolxSoEmx4ZdUMHoZnM85ziCIQ\r\n"
			+ "mm1qJMEYRSKaVqNV6aqdhVpphZVoioQ2B5hKr9SoDLxgbm1p47I0wTXcmsih00UL\r\n"
			+ "2hTS+aJYGflZyeFatHXF55CwL+5Zv3i2uWQUZk3P4g+xOO3p9DNTxq8OATJzcabq\r\n"
			+ "ZjdTywOBu3lQUeYV3V4lqkVom0j0R1YViMpCcQLtOb+FtoMe+v4zEhEF+GyxmXVT\r\n"
			+ "EjZAUm29T1oKb6uJDGunWvFm7z36gcaPUyy14xEm/hxsb2EwdWz/4WQU3r68L0lL\r\n"
			+ "mlvGpmzVAoGBAM6aorGMb+KaRCAVE8CQ5KrqkrmO01GEQuV4Pifv+BqsNovgSrXP\r\n"
			+ "7LMmgS9MwPApgI/vE/In1QiGwf9J+Xe5fM37+lS7u5LyYrnyfzPQhvUP0s5WoBnk\r\n"
			+ "MkjMalMcEdSS2MF+Sma1OY8ATOkvRKY/fixvmUrdxJYtyY5VqeEWeQFjAoGBAMz7\r\n"
			+ "UGwhwSbjYatbTGvfQvR7KBYpcuLAINkq5bejg8q2pFRbguV0vjEUpqkAJnMgTm1Z\r\n"
			+ "/YCmPaN9aOrYYE418kVkqYPA7+rasK+ukVcErdmNo8H/jZqJ4YpjPWXZQxDYz+rT\r\n"
			+ "bpjV0KXtQzIzBLekNOQCQC1t+ROmIt064WMkigiHAoGBAI0eGlpjrlh9PmMbgm5Z\r\n"
			+ "iZckuit8XuKjEH5kbzAgPLsbPfa0sNMWvnpvpnWEW5SvBF1L07fndR9pzEBvarJY\r\n"
			+ "Q+L+ZbJSuIBahwyzkib2G/hvYnIFwT1uHNqUpVs3JQapdtxTQsZ8ql/+uInd+2oC\r\n"
			+ "nFAFuhLk/04E4FMUOADCtMw5AoGAelQUx6NRSOrz07aVod7Ib2y8HJW52TZ9o/yk\r\n"
			+ "wPYVZbmf0jQWpucHxfSkDUFosNX71+iXj9ol+NaT8WsV9jbdkaokO9u9kjPWs9GD\r\n"
			+ "LSI+uhF8Q/9Fu49DiTn2XzzMeFZbprFtVEptzUgODPxDeXDGm7f4IuR5e89vCQXl\r\n"
			+ "kiwxMCsCgYEAm2I+mAYFBKdSlZZHHy46LoIBRegfgam+xuONrXf70v3Fc+pHujcL\r\n"
			+ "dFa6k5GPd7OVhlqB+a//SMbQaCg0Jf6ldPkNddOBnoZO6p/CqUFninF5ClQefZZc\r\n"
			+ "NZTaQVuzo1iTWp0oU4gOZ+0z1n/1S6qIfL6cEWjc7JYD9VpXJQG8hi0=\r\n"
			+ "-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApW4MKpdPygBSmvgVn3oW\r\n"
			+ "kOkWoTN5myWAgOkWJ7yrB9XjtHZIXV5bstJfkJIPFdpyzH5WBUDJzXNy+u2MnGNi\r\n"
			+ "u5UE8bsjKHLbLBKxMLIz3YOtAgvRmTEq0sschdB05c2/6ORCrlp3zsd0/0C8/XZw\r\n"
			+ "TKZOPifeyz7Sj44WkQwSCyqOMkrBW2Pp1mwrentHPHWSS1iX2vYWrBLuW6PK1QOT\r\n"
			+ "x/HRVC+2OtlnZJrKSPoftyNP7/kUNWJZXDimbbadWiQWfd6uhTO7AxtZh1lW6Umt\r\n"
			+ "2O7ThzhG/cD6w1LfDvdNIW9eocxPlWWvnCpSVUdKZykOhtduil5xrVzJbEwcTTjT\r\n"
			+ "NQIDAQAB\r\n"
			+ "-----END PUBLIC KEY-----";

}
