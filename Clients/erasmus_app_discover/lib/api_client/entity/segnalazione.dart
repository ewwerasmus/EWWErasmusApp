class Segnalazione {
  Segnalazione(this.id,
      this.descrizione,
      this.urgenza,
      this.statoSegnalazione);

  int id;
  String descrizione;
  int urgenza;
  StatoSegnalazione statoSegnalazione;
}

enum StatoSegnalazione { DA_RISOLVERE, IN_RISOLUZIONE, RISOLTO }

extension StatoSegnalazioneExt on StatoSegnalazione {
  int get code {
    switch (this) {
      case StatoSegnalazione.DA_RISOLVERE:
        return 0;
      case StatoSegnalazione.IN_RISOLUZIONE:
        return 1;
      case StatoSegnalazione.RISOLTO:
        return 2;
    }
  }

  String get label {
    return this.toString().split(".")[1].split("_").join(" ");
  }
}
