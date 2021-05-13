#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main(void) {
   int i, j;
   int userInput = 20;
   printf("Enter the password length > ");
   scanf("%d", &userInput);
   srand((unsigned int)time(NULL));
   for (j = 0; j < 10; j++) {
      printf("%d. >  ", j + 1);
      for (i = 0; i < userInput; i++) {
         if (rand() % (2 - 0 + 1) == 0) {
            printf("%c", rand() % ('Z' - 'A' + 1) + 'A');
         } else if (rand() % (2 - 0 + 1) == 1) {
            printf("%c", rand() % ('z' - 'a' + 1) + 'a');
         } else {
            printf("%c", rand() % ('9' - '0' + 1) + '0');
         }
      }
      printf("\n");
   }
   system("pause");
   
   return 0;
}