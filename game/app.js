const gameContainer = document.querySelector(".game-container")
const gameListContainer = document.querySelector(".game-list-container")
const backButton = document.querySelector("#back-button")
const gameList = document.querySelector("#game-list")
const table1 = document.querySelector("#table-1")
const table2 = document.querySelector("#table-2")
const scoreTeam1 = document.querySelector("#team-1-global-score")
const scoreTeam2 = document.querySelector("#team-2-global-score")
const foulTeam1 = document.querySelector("#team-1-fouls")
const foulTeam2 = document.querySelector("#team-2-fouls")
const teamName1 = document.querySelector("#team-1-name")
const teamName2 = document.querySelector("#team-2-name")
const endGameButton = document.querySelector("#end-game")
var gameId = 79;

var foulTeam1Holder = 0;
var foulTeam2Holder = 0;
var scoreTeam1Holder = 0;
var scoreTeam2Holder = 0;

var gteams = [];
const game = {};

const addr = "http://localhost:8082/";

const buildList = (arr,list) => {
    arr.forEach( (e) =>{
        const { game_id, arena_name, date } = e
        let li = document.createElement("li")
        let a = document.createElement("a")
        a.href = "#"
        a.onclick = (el) => {
            gameId = game_id
            makeRequest(game_id)
        }
        a.textContent = `GAME - ${date} - AT ${arena_name}`
        li.appendChild(a)
        list.appendChild(li)
    })
}

const insertGameData = (arr) => {
    arr.forEach( (e,index) => {
        let { table, teamName, players } = e
        e.tn.textContent = `${teamName}`
        while (table.firstChild) {
            table.removeChild(table.firstChild);
        }
        players.forEach( el => {
            let row = table.insertRow(table.rows.length)
            let player = document.createElement("p")
            let number = document.createElement("p")
            let score = document.createElement("p")
            let fouls = document.createElement("p")
            let scoreButton = document.createElement("button")
            let foulButton = document.createElement("button")
            number.textContent = el.player_number
            player.textContent = el.player_name
            el.score = 0
            el.fouls = 0
            score.textContent = el.score
            fouls.textContent = el.fouls
            scoreButton.textContent = "SCORE"
            foulButton.textContent = "FOUL"
            scoreButton.onclick = (index) => {
                el.score +=2
                score.textContent = `${el.score}`
                e.score +=2
                e.scoreD.textContent = `${e.score}`
            }
            foulButton.onclick = (index) => {
                if(el.fouls < 5){
                    el.fouls++
                    fouls.textContent = `${el.fouls}`
                    e.fouls++
                    e.foulD.textContent = `${e.fouls}`
                }
            }
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
        })
    })
}

endGameButton.onclick = () => {
    let winner = game.data.reduce( (prev,curr) => prev.score > curr.score ? prev : curr)
    alert(`Gano el equipo ${winner.teamName} !!!11!1!`)
    const playerss = []
    game.data.forEach( d => {
        let play = []
        d.players.forEach(p => {
            let real_player = {}
            real_player.team_id = p.team_id
            real_player.two_points = p.score
            real_player.fouls = p.fouls
            real_player.player_number = p.player_number
            real_player.contract_id = p.person_id
            real_player.player_name = p.player_name
            // real_player.team_name = p.team_name
            play.push(real_player)
        })
        playerss.push(play)
    })
    const fd = new FormData()
    fd.append("winning_team", winner.team_id)
    fd.append("team_1",game.data[0].team_id)
    fd.append("team_2",game.data[1].team_id)
    fd.append("players1", JSON.stringify({players : playerss[0]}))
    fd.append("players2", JSON.stringify({players : playerss[1]}))
    fd.append("game_id", gameId)
    let url = `${addr}endGame`
    fetch(url, {
        mode: 'no-cors',
        method: 'POST',
        body: fd
    }).then(res => {
        return res.text()
    }).then( json => {
        console.log(json)
    }).catch(err => {
        console.log(err)
    })
    backButton.click()
}

const makeRequest = (gameId) => {
    fetch(`${addr}gameData/${gameId}`)
        .then( response => response.json())
        .then( data => {
            console.log(data)
            let teamNames= []
            console.log(teamNames)
            data.ps.forEach(e => {
                let { team_name } = e
                if(!teamNames.includes(team_name)){
                    teamNames.push(team_name)
                    gteams.push({teamName: team_name, team_id:e.team_id, players:[]})
                }  
                gteams.find( t => t.teamName == team_name).players.push(e)
            })
            return Promise.resolve()
        })
        .then(() => {
            gameContainer.style.display = "flex"
            gameListContainer.style.display = "none"
            backButton.style.display = "block"
            return Promise.resolve()
        })
        .then(() => {
            const tables = [table1,table2]
            const scoreD = [scoreTeam1,scoreTeam2]
            const foulD = [foulTeam1,foulTeam2]
            const teamNames = [teamName1,teamName2]
            console.log(gteams)
            game.data = gteams.map((e,index) => {
                e.table = tables[index]
                e.score = 0
                e.fouls = 0
                e.scoreD = scoreD[index]
                e.foulD = foulD[index]
                e.tn = teamNames[index]
                return e
            })
            console.log(game)
            insertGameData(game.data)
        })
        .catch( err => {
            console.log(err)
        })
}

// insertDummyData(table1,dummyData.one,1)
// insertDummyData(table2,dummyData.two,2)
// makeRequest(gameId);

const openGamesRequest = () => {
    gameContainer.style.display = "none"
    backButton.style.display = "none"
    fetch(`${addr}openGames`)
        .then( res => {
            return res.json()
        })
        .then(json => {
            console.log(json)
            buildList(json,gameList)
        })
}

backButton.onclick = () => {
    gameContainer.style.display = "none"
    backButton.style.display = "none"
    gameListContainer.style.display = "block"
    gteams = []
}

openGamesRequest()