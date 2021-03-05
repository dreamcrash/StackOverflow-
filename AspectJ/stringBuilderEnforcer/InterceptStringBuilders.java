package stringBuilderEnforcer;

public aspect InterceptStringBuilders {

    StringBuilder around(StringBuilder target) :
                  call(public StringBuilder append(String)) &&
                  !within(InterceptStringBuilders) &&
                  target(target){
                      if(target.length() == 0){
                         target.append("!!");
                      }
                      else if(target.length() == 1 || target.charAt(0) != '!' || target.charAt(1) != '!')
                      {
                         target.insert(0, "!!");
                      }
                      return proceed(target);
                 }
}  
