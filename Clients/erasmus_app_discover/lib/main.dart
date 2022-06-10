import 'package:erasmus_app_discover/api_client/entity/segnalazione.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const ErasmusDiscover());
}

class ErasmusDiscover extends StatelessWidget {
  const ErasmusDiscover({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Discover',
      theme: ThemeData(
        primarySwatch: Colors.green,
      ),
      home: Scaffold(
        appBar: AppBar(
          title: Text("Erasmus Discover"),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [],
          ),
        ),
      ),
    );
  }
}