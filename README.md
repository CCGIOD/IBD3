Installation :

  - Créer un dossier Projet avec apache dedans. Supprimer le contenu de webapps (c'est ce qu'on va importer).
  - Dans Eclipse, faire "Import from git" et sélectionner comme dossier : ...../apache-tomcat-6.0.14/webapps/ROOT

Remarques :
  * Normalement, il ne devrait pas y avoir d'erreur de build path ...
  * La compilation se fait par Eclipse directement dans WEB-INF/classes (le script compile.sh permet de le faire)
  * Project -> Clean... semble permettre de re-build (à faire après un pull)
  
  - Aller dans le dossier ROOT et faire ./build.sh après chaque modification

Remarques :
  * Le fichier tunnel.sh est dans le .gitignore
