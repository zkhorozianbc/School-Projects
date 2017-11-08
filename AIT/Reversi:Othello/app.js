// app.js
var rev = require('./reversi.js');
var readlineSync = require('readline-sync');
var fs = require('fs');

readCLI();

function readCLI() {
	if (process.argv[2] != undefined) {
		fs.readFile(process.argv[2], 'utf8', function(err, data) {
	 		if (err) {
	  			console.log('uh oh', err); 
			 } else {
	  			setupGame(data);
			 }
		});
	}
	else {
		setupGame(null);
	}
}
function setupGame(preset) {

	let board = null;
	let playerLetter = null;
	let compLetter = null;
	let boardWidth = null;
	let config = null;
	let scriptedCompMoves = [];
	let scriptedPlayerMoves = [];

	console.log("REVERSI\n");


	if (preset != null) {
		config = JSON.parse(preset);
	}

	//generate board
	if (config != null) {
		playerLetter = config["boardPreset"]["playerLetter"];
		if (playerLetter == "X") {
			compLetter = "O";
		}
		else {
			compLetter = "X";
		}
		board = config["boardPreset"]["board"];
		scriptedCompMoves = config["scriptedMoves"]["computer"];
		scriptedPlayerMoves = config["scriptedMoves"]["player"];
		console.log(playerLetter,scriptedPlayerMoves,scriptedCompMoves);
		

	}
	else {

		let boardWidthInput = readlineSync.question('How wide should the board be? (even numbers between 4 and 26, inclusive)\n');
		while (!(!isNaN(boardWidthInput) && boardWidthInput >=4 && boardWidthInput <= 26 && boardWidthInput % 2 == 0)) {
			boardWidthInput = readlineSync.question('How wide should the board be? (even numbers between 4 and 26, inclusive)\n');
		}
		boardWidth = boardWidthInput;
		board = rev.generateBoard(boardWidth,boardWidth);

		let startMoves = {"O":[[(boardWidth / 2)-1,(boardWidth / 2)-1],[(boardWidth / 2),(boardWidth / 2)]],"X":[[(boardWidth / 2)-1,(boardWidth / 2)],[(boardWidth / 2),(boardWidth / 2)-1]]}

		for (letter in startMoves) {
			for (m in startMoves[letter]) {
				let row = startMoves[letter][m][0];
				let col = startMoves[letter][m][1];
				board = rev.setBoardCell(board,letter,row,col);
			}
		}


		let playerLetterInput = readlineSync.question("Pick your letter: X (black) or O (white)\n");
		while (!(playerLetterInput == "X" || playerLetterInput == "O")) {
			playerLetterInput = readlineSync.question("Pick your letter: X (black) or O (white)\n");
		}
		playerLetter = playerLetterInput;

		if (playerLetter == "X") {
			compLetter = "O";
		}
		else {
			compLetter = "X";
		}
	

	}


	function switchCurrPlayer() {
		if (currPlayer == "player") {
			currPlayer = "computer";
		}
		else {
			currPlayer = "player";
		}
	}

	function pressEnterShowCompMove() {
		return readlineSync.question('Press <ENTER> to show computer\'s move...\n');
	}
	function noValidCompMoves() {
		return readlineSync.question('Computer has no valid moves. Press &lt;ENTER&gt; to continue...\n');
	}
	function noValidPlayerMoves() {
		return readlineSync.question('No valid moves available for you.\n Press <ENTER> to pass...');
	}
	
	function pressEntertoContinue() {
		return readlineSync.question('Press <ENTER> to continue...');
	}

	function invalidMoveDisplay() {
		return console.log("INVALID MOVE. Your move should:\n * be in a  format\n * specify an existing empty cell\n * flip at elast one of your oponent's pieces");
	}

	function gameOver(scores) {
		if (scores["X"] > scores["O"]) {
			console.log(whosWho()["X"] + " WINS!");
		}
		else if (scores["X"] < scores["O"]) {
			console.log(whosWho()["O"] + " WINS!");
		}
		else {
			console.log("DRAW");
		}
		return;
	}

	function whosWho() {
		if (playerLetter == "X") {
			return {"X": "player","O": "computer"};
		}
		else {
			return {"X": "computer","O": "player"};
		}
	}

	function getUserMove() {
		let moveInput = rev.algebraicToRowCol(readlineSync.question("What your move? "));
		while (moveInput === undefined || rev.isValidMove(board,playerLetter,moveInput["row"],moveInput["col"]) == false) {
			invalidMoveDisplay();
			moveInput = rev.algebraicToRowCol(readlineSync.question("What your move? "));
		}
		move = moveInput;
	}


	function findCompMove() {
		let bestMove = undefined;
		let validMoves = rev.getValidMoves(board,compLetter);
		let moveScores = [];

		if (validMoves.length == 0) {
			return bestMove;
		}

		for (let cellIndex = 0; cellIndex < validMoves.length; cellIndex++) {
			let row = validMoves[cellIndex][0];
			let col = validMoves[cellIndex][1];
			let possBoard = rev.setBoardCell(board,compLetter,row,col);
			let lines = rev.getCellsToFlip(possBoard,row,col);
			let cellScore = 0;
			lines.forEach((line) => {
				cellScore += line.length;
			})
			
			moveScores.push(cellScore);
		}
		moveScores = moveScores.map((x) => parseInt(x,10));
		let maxScore = 0;
		moveScores.forEach((x) => {
			if (x > maxScore) {
				maxScore = x;
			}
		});

		bestMove = validMoves[moveScores.indexOf(maxScore)];
		return bestMove;

	}

	function getScores() {
		let scores = rev.getLetterCounts(board);
		console.log("Score\n ===== \n","X: ",scores["X"],"\n","O: ",scores["O"], "\n");
		return scores;
	}

	let consecutivePassesUser = 0;
	let consecutivePassesComp = 0;
	let move = undefined;
	let scores = undefined;
	let currPlayer = whosWho()["X"];
	console.log("Player is ".concat(playerLetter),"\n");
	rev.boardToString(board);



	while (consecutivePassesUser != 2 && consecutivePassesComp != 2) {



		if (currPlayer == "computer") {
			pressEnterShowCompMove();			
			move = findCompMove();
		
			if (move === undefined) {
				noValidCompMoves();
				consecutivePassesComp += 1;
				
			}
			else {

				if (config !== null && scriptedCompMoves.length > 0) {
					if (rev.isValidMoveAlgebraicNotation(board,compLetter,scriptedCompMoves[0])) {
						move = rev.algebraicToRowCol(scriptedCompMoves[0]);
						console.log("Computer Move to " + scriptedCompMoves[0] + " is scripted.");
						board = rev.setBoardCell(board,compLetter,move["row"],move["col"]);
						let cellsToFlip = rev.getCellsToFlip(board,move["row"],move["col"]);
						board = rev.flipCells(board,cellsToFlip);				
						consecutivePassesComp = 0;
					}
					else {
						board = rev.setBoardCell(board,compLetter,move[0],move[1]);
						let cellsToFlip = rev.getCellsToFlip(board,move[0],move[1]);
						board = rev.flipCells(board,cellsToFlip);				
						consecutivePassesComp = 0;
					}
					scriptedCompMoves = scriptedCompMoves.slice(1);
				}
				else {
					board = rev.setBoardCell(board,compLetter,move[0],move[1]);
					let cellsToFlip = rev.getCellsToFlip(board,move[0],move[1]);
					board = rev.flipCells(board,cellsToFlip);				
					consecutivePassesComp = 0;
				}

			
			}
		}



		else {
			let validMoves = rev.getValidMoves(board,playerLetter);
			if (validMoves.length == 0) {
				noValidPlayerMoves();
				consecutivePassesUser += 1;
				
			}

			else {
				if (config !== null && scriptedPlayerMoves.length > 0) {
					
					if (rev.isValidMoveAlgebraicNotation(board,playerLetter,scriptedPlayerMoves[0])) {
						move = rev.algebraicToRowCol(scriptedPlayerMoves[0]);
						console.log("Player Move to " + scriptedPlayerMoves[0] + " is scripted.");
					}
					else {
						getUserMove();
					}
					scriptedPlayerMoves = scriptedPlayerMoves.slice(1);
					
				}

				else {
					getUserMove();
				}

				board = rev.setBoardCell(board,playerLetter,move["row"],move["col"]);
				let boom = rev.getCellsToFlip(board,move["row"],move["col"]);
				board = rev.flipCells(board,boom);
				consecutivePassesUser = 0;
				
				
			}

		}
		switchCurrPlayer();
		rev.boardToString(board);

		scores = getScores();
		if (scores["X"] === 0 || scores["O"] === 0 || rev.isBoardFull(board)) {
			gameOver(scores);
			return;
		}


	}

	gameOver(scores);



	

}

















	

