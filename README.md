# Library_manager

Library Manager to aplikacja do zarządzania zasobami biblioteki. Logika aplikacji bazuje na 4 encjach: 
<br>Origin: - zasób biblioteki, posiada wszelkie informacje typu: tytuł, autor, rok wydania, numer ISBN itd. Jest bazą dla książki (egzemplarza). Generalnie powinien być jeden Origin i wiele książek (czyli jego egzemplarzy).
<br>Book – egzemplarz danego Origin. Np. jest jeden Origin książki „Hobbit” i wiele książek (egzemplarzy), których część jest wypożyczona, cześć dostępna w bibliotece itp.
<br>User – użytkownik biblioteki
<br>Rental – wypożyczenie książki przez użytkownika. 
<br>Do zarządzania zasobami w bazie danych wykorzystano JPA/Hibernate.
<br>

<br>Link do repozytorium Library Manager (front-end):
https://github.com/Jansmig/front2


<b>Instrukcja uruchomienia projektu:</b>
1.	Należy utworzyć foldery na oba repozytoria (front-end, i back-end)
2.	W wierszu poleceń, wchodząc do każdego folderu, należy wykonać komendę „git init”
3.	Następnie w każdym folderze wykonać komendę „git clone [adres repozytorium]”
4.	W MySQL Workbench utworzyć nową bazę danych o poniższych właściwościach:
<br>baza: library_manager
<br>user: library_user
<br>hasło: library_password,
5.	Otworzyć oba projekty w IntelliJ. 
6.	Uruchomić warstwę back-end aby Hibernate utworzył odpowiednie tabele w bazie danych. 
7.	W MySql Workbench zapełnić bazę danych rekordami podanymi w sql w folderze resources repozytorium back-end.
8.	Uruchomić obie warstwy aplikacji (najpierw back-end - ustawiono w properties na porcie localhost:8080, potem front-end – ustawiono na porcie 8081).
9.	Otworzyć przeglądarkę pod adresem: http://localhost:8081/

Warstwa widoku – jest oparta na bibliotece Vaadin, obecnie tylko w podstawowym zakresie. Zakładka Origins jest najbardziej rozbudowana, posiada opcje dodawania, edycji i kasowania Originów (formularz pojawia się po kliknięciu w rekord w tabeli lub przycisk ‘Add new’). Podobne funkcjonalności mogą zostać dodane w pozostałych zakładkach.
Do warstwy wizualnej planowane jest dodanie funkcjonalności, które są już w back-endzie (np. sprawdzanie ratingu w Goodreads, zliczanie ilości dostępnych książek wg. danego statusu itp.). 
<br>
