import 'package:chopper/chopper.dart';

class ApiConfig{
  static const String baseUrl = "http://tuxdave.hopto.org";
  static const int port = 80;
  static const String baseEndpoint = "/api";
  static const String segnalazioneEndpoint = "/segnalazione";
  static const Map<String, String> methodsToEndPoint = {
    HttpMethod.Get:"/query",
    HttpMethod.Post:"/insert",
  };
}