Laboratorium nr 7 - Języki Programowania - 3. semestr

Podczas laboratorium należy zbudować mały rozproszony system wykorzystując technologię RMI. W skład tego systemu wchodzą instancje klas uruchamiane równolegle (na jednym lub na kilku różnych komputerach). System umożliwia zasymulowanie działania klubów poszukiwaczy skarbów.

Specyfikacja problemu:

W systemie wykorzystywany jest następujący model danych:

• Map - Mapa, reprezentowana przez kwadrat składający się z 64 (8x8) sektorów oznaczonych jak pola na szachownicy (dolny lewy to sektor A1, prawy górny to sektor H8). Każdy sektor to kwadrat składający się ze 100 (10x10) pól oznaczonych podobnie jak sektory (dolne lewe to pole A1, gome prawe to pole 110). Na polach sektorów Mapy mogą pojawić się Artefakty. Sektory mogą być przeszukiwane przez członków Klubów, przy czym na ich przeszukiwanie Klub powinien mieć aktywne zezwolenie.

• Artifact - Klasa reprezentująca Artefakt mogący pojawić się na Mapie. Artefakt może być Skarbem, Rupieciem lub Pustką. Wydobycie artefaktu zajmuje jakiś czas (ma ty być zasymulowane poprzez ušpienie wątku na czas określony w przekazanych danych).

• Treasure - Skarb, cas wartościowego, podlegającego kategoryzacji.

• Junk-Rupieć, coś bezwartościowego, podlegającego kategoryzacji. 

• Blank Pustka

• Elementami systemu będą:

• OfficeApp - aplikacja reprezentująca urząd wydający pozwolenia na prowadzenie prac poszukiwawczych oraz monitorujący wyniki tych prac. Implementuje interfejs zdalny 20ffice. Dostarcza graficzny interfejs, na którym widać Mapę z zakresem prowadzonych prac i ich wynikami (na odpowiednich polach pokazywane są znalezione Skarby oraz aktualnie zajmowane sektory przez Kluby). W systemie istnieje tylko jedna instancja OfficeApp
• ClubApp - aplikacja reprezentująca Klub zrzeszający poszukiwaczy. Implementuje interfejs adalny IClub. Dostarcza graficzny interfejs, na którym widać stan prac prowadzony przez należących do klubu Poszukiwaczy w aktualnie obsługiwanych sektorach (pokazywane są wszystkie artefakty znalezione przez poszukiwaczy danego klubu wraz z położeniem tych poszukiwaczy oraz zaznaczeniem aktywnych sektorów przypisanych Klubowi). Klub wie, gdzie działa rejestr RMI z zarejestrowaną namiastką Urzędu. Korzysta z tej namiastki, by zarejestrować się w Urzędzie. Korzystając z namiastki urzędu Klub występuje również o pozwolenie na prowadzenie prac poszukiwawczych w danym sektorze. Każdy klub może prowadzić równolegle prace w co najwyżej dwóch sektorach. Klub, z zadaną częstością, raportuje wyniki poszukiwań do Urzędu (wyniki te wcześniej muszą być zaraportowane w Klubie przez poszukiwaczy, przy czym poszukiwacze raportują klubowi wszystkie znaleziska, zaś klub raportuje urzędowi tylko znalezione Skarby), jak również sygnalizuje Urzędowi zakończenie poszukiwań w danym sektorze. W systemie może istnieć kilka instancji ClubApp.

• SeekerApp - aplikacja reprezentująca Poszukiwacza, który po zarejestrowaniu się w klubie może prowadzić prace w ramach pozwolenia wydanego klubowi. Implementuje interfejs zdalny ISeeker. Poszukiwacz wie, gdzie działa rejestr RMI z zarejestrowaną namiastką Urzędu. Korzysta z tej namiastki, by pozyskać listę namiastk klubów i ostatecznie do któregoś z tych klubów się zapisać/wypisać. Poszukiwacz musi wiedzieć również, gdzie działa rejestr RMI z zarejestrowaną namiastką Świata, by ją pobrać i z niej korzystać. Poszukiwacz dostaje zlecenia z Klubu na eksploracje danego pola w danym sektorze. Eksploracja następuje poprzez komunikację ze Światem. W systemie może istnieć wiele instancji SeekerApp

• WorldApp - aplikacja reprezentująca fizyczny Świat. Implementuje interfejs Iworld. Posiada własną Mapę, którą można skonfigurować, tj, umieścić na jej polach jakieś artefakty. Sposób konfiguracji jest dowolny można Mapę wczytywać w pliku tekstowego (edytowanego jakimś edytorem tekstów, można też dostarczyć graficzny interfejs (pozwalający na edycję mapy w aplikacji). Pod tym względem pozostawiona jest dowolność. Mapa z biegiem czasu będzie podlegać zmianom (znikać z niej będą Artefakty odnajdowane przez Poszukiwaczy). Aktualna mapa powinna być wizualizowana na interfejsie graficznym (tutaj mają być prezentowane tylko artefakty jeszcze nieodkryte, bez Poszukiwaczy ani sektorów zajętych przez Kluby). W systemie istnieje tylko jedna instancja WorldApp.

W trakcie implementacji skorzystano ze skompilowanych klas z dostarczonego modulu treasures.jar.
