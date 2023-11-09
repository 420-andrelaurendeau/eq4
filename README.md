# Points d'action
- Faire une daily chaque samedi à 7 P.M. (période de disponibilités d'une heure après).
- Après avoir fini chaque tâche, notifier l'équipe pour faire un code review dans discord. (Prendre des code reviews quand tu commences à travailler s'il y en a)

# Définition de DONE
- Le code compile
- 80% de coverage dans le test
- Le code est vérifiée par un pair (peer programming ou code review)
- La branche est à jour depuis main
- Changements faits ne doivent pas briser frontend/backend (Test yo shit yo)
- Merge dans main et test le fonctionnement avant de mettre a done
- ACTUALLY USE THE GOD DAMN CHECKLIST

### Frontend:
- Typage complet et logique (pas de any)
- Navigation minimale
- Texte, pas d'icones

### Backend:
- Pas de classes modèles dans le controller
- Service prend DTO et retourne DTO (Wrapped dans un Optional ou Collection)

### Normes de l'équipe:
- Le code est cohérent entre les programmeurs
- DRY
- Respect des normes de la langue de prog
- Pas de code obsolete
- Pas d'imports non-utilisés

# eq4
meilleur que l'équipe 3

- **IDE**: IntelliJ (backend), VSCode (frontend)
- **Version**: Github, Git 2.42.0
- **Backend**: Java 17.0.6, Spring 3.1.3
- **Frontend**: React Typescript
- **Database**: PostGreSQL 15.4
- **Test**: JUnit, Mockito
- **Style**: ??

### VsCode extensions

- **Auto Rename Tag**
- **ESLint**
- **Git Lens**
- **Prettier**
- **ES7 + React/Redux/React-Native snippets**

## Installing PostgreSQL
[Installation link](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)

### Create database
1. $ `psql -U postgres` enter password from installation
3. postgres=# `CREATE USER eq4 WITH PASSWORD 'eq4';`
4. postgres=# `CREATE DATABASE audace OWNER eq4;`
