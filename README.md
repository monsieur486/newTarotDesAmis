# TDA — Spring Boot + Thymeleaf + WebSocket (HTTP POST broadcast)
- **Mobile-first** UI (Bootstrap)
- Tabs: **Public** (lecture seule), **Réunion** (level=1, sécurisé), **Partie** (level=2, sécurisé)
- Formulaire d'édition intégré dans **Réunion** et **Partie**
- Diffusion en temps réel via `/topic/messages` après POST `/edit`
- Footer: état WS (`🔗` connecté / `⛓️‍💥` déconnecté), option `APP_LOG_FOOTER` pour afficher le dernier `MessageDto`

## Local (IDE)
- Lancer `App.main()` (profil par défaut: dev)
- WebSocket autorise toutes origines (`app.allowed-origins="*"`)
- Ouvrir http://localhost:8080

## Production (Docker + Apache2 reverse proxy)
```bash
cp .env.example .env
docker compose up -d --build
```
- Le container écoute **127.0.0.1:8080** (accessible par Apache uniquement)
- Profil actif: `prod` (entêtes proxy pris en compte, cookie Secure)
- WebSocket autorisé pour `APP_ALLOWED_ORIGINS` (défaut: https://tda.tartempion.fr)

### Apache vhost (ISPConfig — directives)
```
ProxyPass /.well-known !
ProxyPreserveHost On
RequestHeader set X-Forwarded-Proto "https"
RequestHeader set X-Forwarded-Port "443"

# WS en priorité
ProxyPass        /ws  ws://127.0.0.1:8080/ws retry=0
ProxyPassReverse /ws  ws://127.0.0.1:8080/ws

# Le reste
ProxyPass        /    http://127.0.0.1:8080/
ProxyPassReverse /    http://127.0.0.1:8080/

<Proxy *>
  Require all granted
</Proxy>
```

## Variables
- `APP_REDACTEUR_USER`, `APP_REDACTEUR_PASS` ou `APP_REDACTEUR_PASS_BCRYPT`
- `APP_LOG_FOOTER` (true/false)
- `APP_ALLOWED_ORIGINS` (CSV patterns pour SockJS, ex: `https://tda.tartempion.fr,http://localhost:8080`)
