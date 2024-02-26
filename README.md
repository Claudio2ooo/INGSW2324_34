# DietiDeals24

# About
DietiDeals24 è una piattaforma dedicata alla gestione di aste online. Questa soluzione è fornita attraverso un'applicazione mobile che offre agli utenti una gamma completa di funzionalità, compresa la creazione di aste all'inglese e al ribasso, consentendo loro di partecipare attivamente all'esperienza di shopping e di fare offerte.

# Features
- Creazione aste all'inglese
- Creazione aste al ribasso
- Fare offerte
- Personalizzare profilo
- Ricevere notifiche

# Build instructions
Per lanciare in esecuzione il Backend di DietiDeals24:
- Specificare una password per il database postgres all'interno di "compose.yaml"
- Riportare la stessa password in /Server/src/main/resources/application.properties
- Impostare una secret key jwt e una expiration (security.jwt.secret-key=<your_secret_key>, security.jwt.expiration-time=<your_expiration>) in /Server/src/main/resources/application.properties
Alla fine di questi passaggi, eseguire "docker compose up" nella root directory della repository.

Per buildare l'apk del client:
- specificare l'indirizzo ip su cui viene eseguito il backend in /Client/app/src/main/java/it/unina/dietideals24/retrofit/RetrofitService.java
- se si vuole tracciare le statistiche di utilizzo, sostituire il google-services.json in /Client/app

# TODO
- subvariation su template cockburn
- rileggere documentazione
- testare istruzioni build su repository appena clonata
