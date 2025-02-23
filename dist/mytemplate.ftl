<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>We Love Board Games</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      /*background: linear-gradient(0.25turn, #3f87a6, #ebf8e1, #f69d3c);*/
      margin: 10px 0;
      padding: 0;
    }

    .all-games-container {
      border: 2px black solid;
      border-radius: 24px;
      background: linear-gradient(0.25turn, #3f87a6, #ebf8e1, #f69d3c);
      width: 80%;
      margin: auto;
      text-align: center;
    }


    .game-title {
      font-size: 1.2rem;
      font-weight: bold;
      color: #000;
      text-align: center;
      margin-top: 10px;
      margin-bottom: 10px;
    }

    .game-container {
      border: 2px black solid;
      display: inline-block;
      width: 400px;
      background: white;
      border-radius: 24px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
      padding: 20px;
      margin: 10px 20px 20px 10px;
    }

    .image-container {
      text-align: center;
      margin-bottom: 20px;
    }

    .game-image {
      max-width: 300px;
      max-height: 300px;
      border-radius: 12px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .game-info {
      text-align: left;
      padding: 2px 0;
      color: #555;
    }

    .game-info strong {
      color: #333;
    }

    .thumbnail-image {
      max-width: 90px;
      max-height: 90px;
      margin: 5px;
    }

    .game-thumbnail {
      display: inline-block;
      margin-bottom: 20px;
    }

    a {
      text-decoration: none; /* Removes underline */
      color: black; /* Dark gray */
      font-weight: bold;
      transition: color 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
    }

    a:hover {
      color: #adadad; /* Dark gray */
    }

    a:active {
      color: black; /* Keep the same color when clicked */
    }

    ul {
      margin-top: 4px;
    }

    li {
      margin-left: -10px;
    }


    table {
      width: 100%;
      border-collapse: collapse;
      font-family: Tahoma, Geneva, sans-serif;
    }

    table td {
      padding: 15px;
    }

    table thead td {
      background-color: #54585d;
      color: #ffffff;
      font-weight: bold;
      font-size: 13px;
      border: 1px solid #54585d;
    }

    table tbody td {
      color: #636363;
      border: 1px solid #dddfe1;
    }

    table tbody tr {
      background-color: #f9fafb;
    }

    table tbody tr:nth-child(odd) {
      background-color: #ffffff;
    }
  </style>

</head>
<body>
<main>
  <div class="all-games-container">
      <#list games as game>
        <div class="game-container">
          <div class="image-container">
            <a href="${game.boardGameGeekUrl}">
              <img class="game-image" src="${game.imageUrl}" title="${game.name}" alt="${game.name}">
            </a>
          </div>
          <div class="thumbnails-container">
              <#list game.linkedGames as linkedGame>
                <div class="game-thumbnail">
                  <a href="${linkedGame.boardGameGeekUrl}">
                  <img alt="${linkedGame.name}" class="thumbnail-image" src="${linkedGame.thumbnailUrl}">
                  </a>
                </div>
              </#list>
          </div>
          <div class="game-info-container">
              <table>
                <tbody>
                <tr>
                  <td>
                    Minimum Age
                  </td>
                  <td>
                      ${game.minimumAge}
                  </td>
                </tr>
                <tr>
                  <td>
                    Players
                  </td>
                  <td>
                      ${game.minimumNumberOfPlayers} - ${game.maximumNumberOfPlayers} (${game.bestNumberOfPlayers!""})
                  </td>
                </tr>
                <tr>
                  <td>Year Published</td>
                  <td>${game.yearPublished}</td>
                </tr>
                </tbody>
              </table>

          </div>

        </div>
      </#list>
  </div>
</main>
</body>
</html>
