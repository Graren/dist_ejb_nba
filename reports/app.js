const bestTeam = document.querySelector("#best-team")
const mostWins = document.querySelector("#most-wins")
const worstTeam = document.querySelector("#worst-team")
const leastWins = document.querySelector("#least-wins")
const mvpList = document.querySelector(".mvp-list")
const mostFoulsTeam = document.querySelector("#most-fouls-team")
const foulsTeam = document.querySelector("#fouls-team")
const mostFoulsGame = document.querySelector("#most-fouls-game")
const foulsGame = document.querySelector("#fouls-game")

const addr = "http://localhost:8082/";

const requestTeams = () => {
    fetch(`${addr}teamWins`)
        .then(res => {
            return res.json()
        })
        .then( json => {
            let best = json[0]
            let worst = json[json.length-1]
            bestTeam.textContent = `${best.team} : ${best.count} ${best.count === '1' || best.count === 1 ? "win": "wins"}`
            worstTeam.textContent = `${worst.team} : ${worst.count} ${worst.count === '1' || worst.count === 1 ? "win": "wins"}`
        })
        .catch(e => {
            console.log(e)
        })
}

const requestPlayers = () => {
    fetch(`${addr}stats/players`)
        .then(res => {
            return res.json()
        })
        .then( json => {
            json.forEach( (player, index) =>{
                let { two_points, player_name } = player
                let h4 = document.createElement("h4")
                h4.textContent = `${index + 1} - ${player_name} : ${two_points} points`
                mvpList.appendChild(h4)
            })
        })
        .catch(e => {
            console.log(e)
        })
}

const requestTeamFouls = () => {
    fetch(`${addr}teamFouls`)
        .then(res => {
            return res.json()
        })
        .then( json => {
            let { team, fouls } = json
            mostFoulsTeam.textContent = `${team} : ${fouls} fouls`
        })
        .catch(e => {
            console.log(e)
        })
}

const requestGameFouls = () => {
    fetch(`${addr}gameFouls`)
        .then(res => {
            return res.json()
        })
        .then( json => {
            let { arena_name, fouls, date } = json
            mostFoulsGame.textContent = `Game played on ${arena_name} on ${date} with ${fouls} fouls`
        })
        .catch(e => {
            console.log(e)
        })
}

requestTeams()
requestPlayers()
requestTeamFouls()
requestGameFouls()