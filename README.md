⚙️ Compilation et Exécution

1. Compilation
Avant d'exécuter les fichiers, assurez-vous d'être dans le répertoire src :
$ cd src
Puis compile l’ensemble du projet avec :
$ javac -cp ".:../lib/*" blocksworld/executable/*.java
2. Exécution des fichiers principaux
Les fichiers exécutables se trouvent dans le dossier blocksworld/executable. Pour exécuter un programme, utilisez la commande suivante :
$ java -cp ".:../lib/*" blocksworld.executable.NomDuFichier
Par exemple :
$ java -cp ".:../lib/*" blocksworld.executable.PlannerMain
