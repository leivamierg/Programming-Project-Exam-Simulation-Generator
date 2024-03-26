# Programming-Project-Exam-Simulationm-Simulator
Free Choice-Group project for the "Programming Project" course of the Free University of Bozen-Bolzano, 2nd semester 2023-2024. 

## come funzionano pull requests in github?
Pull requests ti permettono di far vedere agli altri i cambiamenti che hai fatto tu `sul tuo branch` prima di unirli al branch main.
Sotto il tab `Pull request` su github viene creata automaticamente una discussione per ogni singola pull request, dove si puo discutere cosa fanno i cambiamenti e chiedere chiarimenti.

### come creare una pull request?
1. vai sulla pagina principale del repository
2. clicca sul tab `Pull requests`
3. clicca su `New pull request`
4. seleziona il branch che vuoi unire al branch main
5. clicca su `Create pull request`
6. scrivi un titolo e una descrizione per la tua pull request
7. clicca su `Create pull request`

in alternativa, puoi creare una pull request direttamente dal terminal con la github cli:
```bash
# installa la github cli
sudo apt update
sudo apt install gh
# crea la pull request (si deve essere dentro la repository)
gh pr create --title "Your PR title" --body "Your PR description"
```