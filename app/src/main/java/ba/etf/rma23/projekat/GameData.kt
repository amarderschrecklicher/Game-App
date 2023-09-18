package ba.etf.rma23.projekat

import ba.etf.rma23.projekat.data.repositories.GamesRepository


class GameData {

companion object gameObject{

           var games : List<Game> = emptyList()

           suspend fun getAll(): List<Game>{

               games = GamesRepository.getGamesByName("a")
               return  games

         }


    fun candyCrush(): List<UserImpression> {
        return listOf(
            UserRating("June Flynt",12005599,5.0),
            UserRating("Ahmad Woods",12005567,5.0),
            UserReview("June Flynt",12005533,"Keeps my attention and helps me focus and have fun. It's challenging, but not like some games where I'm stuck on one level for days because not enough lives and very aggravating when you get to a few hundred levels and then you get stuck and can't continue til you pass a level. But what if someone can't for some reason pass a level . Then I just give up the game that once was fun. But when it's to difficult after 30 plus tries, would be great to have a free pass somehow.. B 4 in other games."),
            UserRating("Janiston Kniola",12005578,4.0),
            UserReview("Dmitriy Kutepov",12005588,"I have been playing for a while and I wish some of the levels were made easier, especially the nightmare difficulty. It seems they are made impossible unless you spend money on boosters or extra moves. I don't feel that is right especially when its one after another back to back, and I did pay to pass the previous level.... Getting to the point when I will just delete the game and forget about it..."),
            UserRating("Leigh Sandt",12005565,5.0),
            UserReview("Crystol Woods",12005550,"It's a fun,relaxing game that speaks to my sense of order and need to organize. The drawbacks are that after a certain level the game becomes competitive, even if it's just against yourself! Most of the levels require boosters to beat which you can win free but the temptation is there to buy. If you don't want to spend real money on a game then resign yourself to win the levels without boosters. Over all addictive and entertaining. If you find level too hard w/o \$\$...just start over!"),
            UserReview("Adam Sandt",12005511,"Love the smoothness of the game. Nice grafics and interesting events. The fishes at times leaves me frustrated when it seems that they just doesn't go where I'd hoped. And can't use candies to create fishes. The new Superstars event has made me very, very excited. Thank you for this awesome event. This a one of kind event, speaking for myself, I haven't heard or came across this kind of event in all the games I've played and I've played a lot. Superbly done"),
            UserRating("Dom Kutepov",12005520,1.0),
            UserReview("Jenni Kniola",12005533,"I love this game... I have for years, but I hate when the option to watch a video for extra moves disappears... I don't know why it does that. It will appear for a long time, then just disappear for weeks... this makes it ridiculously hard to beat the levels without having to buy gold in order to continue playing. I'm debating on taking a break from it for a while.")
            )
    }
    fun papersPlease(): List<UserImpression> {
        return listOf(
            UserRating("Dominic Wells", 12005566, 3.0),
            UserRating("Brooke Turtle", 12005590, 3.0),
            UserReview("Dom Wells",12005587 ,"I absolutely love the game, the artstyle and gameplay are just magnificent. My only problem is that at least once per in-game day the game will just randomly have a frame rate crash and can only be fixed when exiting out of the app and opening it back up. I'm glad the game saves after every person because if it didn't this game would be practically unplayable. However this is a simple bug fix and once that's out of the way you're working with an easy 5 star game here"),
            UserReview("Hazel Baumgartner", 12005577, "This game is frustrating, but in a good way. Having to focus on the repetitive daily task of (you guessed it) checking papers while also watching the story unfold and somehow controlling the fate of a nation (or at the very least your family) from behind a desk is a really cool concept. It's harder than you think though. Took me about eight tries before I reached an ending that didn't have me dead or in prison. Will be trying again to get other good endings since there are like 150."),
            UserRating("Dan Baumgartner", 12005564, 5.0),
            UserReview("Brov Turtle", 12005544, "I love this on pc and wanted to try it on mobile too. The layout is different which is expected, but I can't get past the fact you are stuck looking at the notice, audio script file, or the rule book when not looking at passports/tickets/etc. I want to close them out and have a blank space to look at unless I click something directly to look at. Please allow this and I'll give 5 stars."),
            UserReview("Ri Wo", 12005540, "So happy that this is on the Google Play store! I've seen some of my fav YouTubers play this & I always wanted to try it myself, but don't have a gaming computer. Nor do I have Steam. Now that I've been able to play it, it's fun! A little intense at times, but as I've watched the gameplay b4, this was expected. I really like the characters. Especially the storylines of certain ones. The only change I'd make is probs just to emphasize the 3 bullets in the tranquilizer gun."),
            UserRating("Ri Tu", 12005521, 5.0),
            UserReview("Eowynn Zu", 12005507, "As someone who loved the game since kubzscouts or jay played it many years ago, I was joyed that I found it on mobile as it's more accessible than playing on my computer, this port is very good, it changes the booth upgrades to fit playing without a keyboard and using your hands/fingers. It also keeps most events from the original computer game, which is nice as some ports change events. I honestly recommend the game if you enjoy detailed semi-repetitive game play that does not get boring!")
            ,
            UserRating("Eow Hu", 12005517, 5.0),
        )
    }
    suspend fun getDetails(title: String): Game {
        val gameList: ArrayList<Game> = arrayListOf()
        gameList.addAll(getAll())
        val game = gameList.find { game-> title == game.name }
        return game?: Game(-1,"",null,null,0.0,null,null,null,null ,"","",null)

    }
    }
}