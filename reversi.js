// reversi.js

function repeat(value, n) {
	let arr = [];
	for (let i = 0; i < n; i++) {
		arr.push(value);
	}
	return arr;
}

function generateBoard(rows, columns, initialCellValue=" ") {
	return repeat(initialCellValue,rows*columns);
}


function rowColToIndex(board, rowNumber, columnNumber) {
	return (Math.sqrt(board.length) * rowNumber) + columnNumber;
}

function indexToRowCol(board, i) {
	return {"row": Math.floor(i / Math.sqrt(board.length)),"col":i % Math.sqrt(board.length)};
}

function setBoardCell(board, letter, row, col) {
	let newBoard = board.slice();
	newBoard[rowColToIndex(newBoard,row,col)] = letter;
	return newBoard;
}

function algebraicToRowCol(algebraicNotation) {
	if (algebraicNotation.match('[A-Z][0-9]')) {
		return {"row":parseInt(algebraicNotation[1])-1,"col":algebraicNotation.charCodeAt(0)-65};
	}
	else {
		return undefined;
	}
}

function placeLetters(board, letter, ...algebraicNotation) {
	let newBoard = board;
	for (let i = 0; i < algebraicNotation.length; i++) {
		let rowCol = algebraicToRowCol(algebraicNotation[i])
		newBoard = setBoardCell(newBoard,letter, rowCol["row"], rowCol["col"]);
	}
	return newBoard;
}


function boardToString(board) {
    let n = Math.sqrt(board.length);
    let uppers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    let border = "   " + "+---".repeat(n) + "+";

    
    let colLabels = "  ";
    for (let i = 0; i < n; i++) {
        colLabels += "   " + uppers[i];
    }
    
    console.log(colLabels);
    console.log(border);

    for (let i = 0; i < n; i++) {
        let line = (i+1) + " ";
        if (i < 9) {
        	line = " " + line;
        }
        for (let j = 0; j < n;j++) {
            line += "| " + board[rowColToIndex(board,i,j)] + " ";
        }
        line += "|"
        console.log(line);
        console.log(border);
    }
}


function isBoardFull(board) {
	for (let i = 0; i < board.length; i++) {
		if (board[i] == " ") {
			return false;
		}
	}
	return true;
}

function flip(board, row, col) {
	let i = rowColToIndex(board,row,col);
	if (board[i] == "O") {
		board[i] = "X";
	}
	else if (board[i] == "X") {
		board[i] = "O";
	}
	else {
		return board;
	}		

	return board;
}

function flipCells(board, cellsToFlip) {
	for (let group of cellsToFlip) {
		for (let cell of group) {
			board = flip(board, cell[0], cell[1]);
		}
	}
	return board;
}




function getCellsToFlip(board, lastRow, lastCol) {
	let player = board[rowColToIndex(board,lastRow,lastCol)];
	let groups = [];



	function canAdvanceInPath(cell,d) {
		let cellIndex = rowColToIndex(board,cell[0],cell[1]);
		let r = d[0];
		let c = d[1];
		let sq = Math.sqrt(board.length);
		let rValid = true;
		let cValid = true;

		if (r === 1) {
			rValid = cellIndex < (board.length - sq);
		}
		if (r === -1) {
			rValid = cellIndex >= sq;
		}

		if (c === 1) {
			cValid = (cellIndex + 1) % sq !== 0;
		}

		if (c === -1) {
			cValid = cellIndex % sq != 0;
		}

		
		return rValid && cValid;
	}


	let directions = [[0,1],[0,-1],[1,0],[-1,0],[1,1],[1,-1],[-1,-1],[-1,1]];

	for (let direction of directions) {
		let currCell = [lastRow,lastCol];
		let line = [];

		while (canAdvanceInPath(currCell,direction)) {

			currCell[0] += direction[0];
			currCell[1] += direction[1];
			

			
			if (board[rowColToIndex(board,currCell[0],currCell[1])] === " ") {
				break;
			}

			else if (board[rowColToIndex(board,currCell[0],currCell[1])] === player) {
				if (line.length > 0) {
					groups.push(line);
				}
				break;
				

			}
			else {
				
				line.push([currCell[0],currCell[1]]);

			}
		
		}
	}


	return groups;			
	
}


function isValidMove(board, letter, row, col) {
	let potMoveIndex = rowColToIndex(board,row,col);
	if (potMoveIndex >= 0 && potMoveIndex < board.length && board[potMoveIndex] === " ") {
		return getCellsToFlip(setBoardCell(board,letter,row,col),row,col).length > 0;
	}
	return false;
}

function isValidMoveAlgebraicNotation(board, letter, algebraicNotation) {
	let potMoveRowCol = algebraicToRowCol(algebraicNotation);
	let row = potMoveRowCol["row"];
	let col = potMoveRowCol["col"];
	let potMoveIndex = rowColToIndex(board,row,col);
	if (potMoveIndex >= 0 && potMoveIndex < board.length && board[potMoveIndex] === " ") {
		return getCellsToFlip(setBoardCell(board,letter,row,col),row,col).length > 0;
	}
	return false;
}
function getLetterCounts(board) {
	let x = 0;
	let o = 0;
	for (let letter of board) {
		if (letter == "X") {
			x += 1;
		} 
		if (letter == "O") {
			o += 1;
		}
	}
	return {"X":x,"O":o};
}


function getValidMoves(board, letter) {
	let validMoves = [];
	for (let i = 0; i < board.length; i++) {
		let rowCol = indexToRowCol(board,i);
		if (isValidMove(board,letter,rowCol["row"],rowCol["col"])) {
			validMoves.push([rowCol["row"],rowCol["col"]]);
		}
	}
	return validMoves;
}

module.exports = {
	repeat: repeat,
	generateBoard: generateBoard,
	rowColToIndex,rowColToIndex,
	indexToRowCol: indexToRowCol,
	setBoardCell: setBoardCell,
	algebraicToRowCol: algebraicToRowCol,
	placeLetters: placeLetters,
	boardToString: boardToString,
	isBoardFull: isBoardFull,
	flip: flip,
	flipCells: flipCells,
	getCellsToFlip: getCellsToFlip,
	isValidMove: isValidMove,
	isValidMoveAlgebraicNotation: isValidMoveAlgebraicNotation,
	getLetterCounts: getLetterCounts,
	getValidMoves: getValidMoves
}

















