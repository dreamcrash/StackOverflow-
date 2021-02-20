/** Original Question : https://stackoverflow.com/questions/65240923

Password Generating program in python

I have generated random passwords. Now i want to assign each password 
to each/different users present in the users list. 
but only the last password is being assigned to each user. The code is below.

*/

user_passwords = {}
for user in users:
    for i in range(0, pass_len):
        user_passwords[user] = user_passwords.get(user, "") + random.choice(chars)

for user in users:
    print(user," Password : ",user_passwords[user])
