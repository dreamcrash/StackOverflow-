/** Original Question : https://stackoverflow.com/questions/65311742/

End a function when the user provides a certain keyword as input

How can I end a function when a user types a specific keyword (e.g. 'off') 
in an input without having to add the necessary code within each input? 
Basically I want the function to completely stop when a user types the word 'off' 
anywhere in any of the inputs below. I have created a function (see bottom of code) 
and have tried placing/calling it throughout my code but am not sure exactly 
where to put it or if I can even do this with a function? If not, how can I achieve this?

**/

def my_input(*args, **kwargs):
    str = input(*args, **kwargs)
    if str == "off":
        exit()
    return str

def order_coffee():

    drink = my_input("What would you like? (espresso/latte/cappuccino): ")

    user_owes = int(0)

    if drink == "espresso":
        user_owes = round(1.5, 2)
    elif drink == "latte":
        user_owes = round(2.5, 2)
    elif drink == "cappuccino":
        user_owes = round(3.0, 2)

    print(f"Please insert coins, you owe ${user_owes}.")
    quarters = (int(my_input("How many quarters?: ")) * .25)
    dimes = (int(my_input("How many dimes?: ")) * .1)
    nickels = (int(my_input("How many nickels?: ")) * .05)
    pennies = (int(my_input("How many pennies?: ")) * .01)

    user_paid = round(quarters + dimes + nickels + pennies, 2)
    print(f"You have inserted ${user_paid}.")
    change = round(user_paid - user_owes, 2)

    if user_paid >= user_owes:
        print(f"Here is ${change} in change.")
        print(f"Here is your {drink} ☕. Enjoy!")
        order_coffee()
    else:
        print("Sorry that's not enough money. Money refunded.")
        order_coffee()
        
order_coffee()

 def off():
        if input == "off":
            return
