const table1 = document.querySelector("#table-1")
const table2 = document.querySelector("#table-2")
const scoreTeam1 = document.querySelector("#team-1-global-score")
const scoreTeam2 = document.querySelector("#team-2-global-score")
const foulTeam1 = document.querySelector("#team-1-fouls")
const foulTeam2 = document.querySelector("#team-2-fouls")
const endGameButton = document.querySelector("#end-game")

var foulTeam1Holder = 0;
var foulTeam2Holder = 0;
var scoreTeam1Holder = 0;
var scoreTeam2Holder = 0;

const dummyData = {
    one: [
        {
            number: 1,
            player: "aaaaaaaaaaaaaaaaaaaaaaaaaaaa",
        },
        {
            number: 2,
            player: "bbbbbbbbbbbbbbbbbbbbbb",
        },
        {
            number: 3,
            player: "cccccccccccccccccccccc",
        },
        {
            number: 4,
            player: "dddddddddddddddddddddd",
        },
        {
            number: 5,
            player: "eeeeeeeeeeeeeeeeeee",
        }
    ],
    two: [
        {
            number: 1,
            player: "affffffffffffffffffff",
        },
        {
            number: 2,
            player: "bxxxxxxxxxxxxxxxxxxxxx",
        },
        {
            number: 3,
            player: "czzzzzzzzzzzzzzzzzzzz",
        },
        {
            number: 4,
            player: "d",
        },
        {
            number: 5,
            player: "e",
        }
    ]
}


const insertDummyData = (table,arr,team) => {
    arr.forEach(function(element,index) {
        var row = table.insertRow(table.rows.length)
        let player = document.createElement("p")
        let number = document.createElement("p")
        let score = document.createElement("p")
        let fouls = document.createElement("p")
        let scoreButton = document.createElement("button")
        let foulButton = document.createElement("button")
        number.textContent = element.number
        player.textContent = element.player
        player.team = team
        score.score = 0
        fouls.fouls = 0
        score.textContent = score.score
        fouls.textContent = fouls.fouls
        scoreButton.onclick = (index) => {
            score.score+=2
            score.textContent = `${score.score}`
            if(player.team == 1){
                scoreTeam1Holder+=2
                scoreTeam1.textContent = `${scoreTeam1Holder}`
            }
            else{
                 scoreTeam2Holder+=2
                 scoreTeam2.textContent = `${scoreTeam2Holder}`
            }
        }
        scoreButton.textContent = "SCORE"
        foulButton.onclick = (index) => {
                fouls.fouls++
                fouls.textContent = `${fouls.fouls}`
                if(player.team == 1){
                    foulTeam1Holder++
                    foulTeam1.textContent = `${foulTeam1Holder}`
                }
                else{
                    foulTeam2Holder++
                    foulTeam2.textContent = `${foulTeam2Holder}`
                }
        }

        foulButton.textContent = "FOUL"
        stuff = [
            number,
            player,
            score,
            fouls,
            scoreButton,
            foulButton
        ]
        for(i = 0; i < 6 ; i++){
            let cell = row.insertCell(i)
            cell.appendChild(stuff[i])
        }
    }, this);
}

endGameButton.onclick = () => {
    alert("Game ended wiii")
}

insertDummyData(table1,dummyData.one,1)
insertDummyData(table2,dummyData.two,2)

