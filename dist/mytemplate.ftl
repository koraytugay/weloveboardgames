<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>We Love Board Games</title>
  <style>
    * {
      box-sizing: border-box;
    }
    body {
      font-family: Arial, sans-serif;
      /*background: linear-gradient(0.25turn, #3f87a6, #ebf8e1, #f69d3c);*/
      margin: 10px 0;
      padding: 0;
    }

    .controls-container {
      width: 90%;
      margin: 20px auto;
      padding: 20px;
      background: white;
      border-radius: 12px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    .filter-group {
      display: inline-block;
      margin: 10px 20px;
      vertical-align: top;
    }

    .filter-group label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
      color: #333;
    }

    .filter-group input, .filter-group select {
      padding: 8px;
      border: 1px solid #ddd;
      border-radius: 4px;
      font-size: 14px;
      width: 200px;
    }

    .sort-group {
      display: inline-block;
      margin: 10px 20px;
      vertical-align: top;
    }

    .sort-group label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
      color: #333;
    }

    .sort-group select {
      padding: 8px;
      border: 1px solid #ddd;
      border-radius: 4px;
      font-size: 14px;
      width: 200px;
    }

    .stats-container {
      width: 90%;
      margin: 10px auto;
      padding: 15px;
      background: #f0f0f0;
      border-radius: 8px;
      text-align: center;
      font-weight: bold;
    }

    .all-games-container {
      border: 2px black solid;
      border-radius: 24px;
      background: linear-gradient(0.25turn, #3f87a6, #ebf8e1, #f69d3c);
      width: 90%;
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

    .recommendation-info {
      background: #f9f9f9;
      padding: 10px;
      margin-top: 10px;
      border-radius: 8px;
      text-align: left;
      font-size: 0.9em;
      color: #555;
    }

    .recommendation-info strong {
      color: #000;
    }

    .badge {
      display: inline-block;
      padding: 3px 8px;
      margin: 2px;
      border-radius: 12px;
      font-size: 0.85em;
      font-weight: bold;
    }

    .badge-rating {
      background: #4CAF50;
      color: white;
    }

    .badge-complexity {
      background: #FF9800;
      color: white;
    }

    .badge-time {
      background: #2196F3;
      color: white;
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

    .hidden {
      display: none !important;
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
  <div class="controls-container">
    <h2 style="text-align: center; margin-top: 0;">We Love Board Games - Recommendations</h2>

    <div class="filter-group">
      <label for="searchInput">Search:</label>
      <input type="text" id="searchInput" placeholder="Search games...">
    </div>

    <div class="filter-group">
      <label for="minRatingFilter">Min Rating:</label>
      <input type="number" id="minRatingFilter" min="0" max="10" step="0.1" placeholder="e.g., 7.0">
    </div>

    <div class="filter-group">
      <label for="minComplexityFilter">Min Complexity:</label>
      <input type="number" id="minComplexityFilter" min="1" max="5" step="0.1" placeholder="e.g., 2.0">
    </div>

    <div class="filter-group">
      <label for="maxComplexityFilter">Max Complexity:</label>
      <input type="number" id="maxComplexityFilter" min="1" max="5" step="0.1" placeholder="e.g., 3.0">
    </div>

    <div class="filter-group">
      <label for="maxTimeFilter">Max Time (min):</label>
      <input type="number" id="maxTimeFilter" min="0" step="15" placeholder="e.g., 120">
    </div>

    <div class="sort-group">
      <label for="sortSelect">Sort By:</label>
      <select id="sortSelect">
        <option value="score-desc">Recommendation Score (High to Low)</option>
        <option value="rating-desc">BGG Rating (High to Low)</option>
        <option value="complexity-asc">Complexity (Low to High)</option>
        <option value="complexity-desc">Complexity (High to Low)</option>
        <option value="time-asc">Play Time (Short to Long)</option>
        <option value="name-asc">Name (A to Z)</option>
      </select>
    </div>
  </div>

  <div class="stats-container">
    Showing <span id="visibleCount">${games?size}</span> of ${games?size} games
  </div>

  <div class="all-games-container">
      <#list games as game>
        <div class="game-container"
             data-name="${game.name?lower_case}"
             data-rating="${game.averageRating!""}"
             data-complexity="${game.complexity!""}"
             data-time="${game.playingTime!""}"
             data-score="${game.linkScore?c}">
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
                <#if game.averageRating??>
                <tr>
                  <td>BGG Rating</td>
                  <td>
                    <span class="badge badge-rating">${game.averageRating}</span>
                  </td>
                </tr>
                </#if>
                <#if game.complexity??>
                <tr>
                  <td>Complexity</td>
                  <td>
                    <span class="badge badge-complexity">${game.complexity}/5</span>
                  </td>
                </tr>
                </#if>
                <#if game.playingTime??>
                <tr>
                  <td>Play Time</td>
                  <td>
                    <span class="badge badge-time">${game.playingTime} min</span>
                  </td>
                </tr>
                </#if>
                </tbody>
              </table>

              <div class="recommendation-info">
                <strong>Why Recommended:</strong><br/>
                Recommended by ${game.linkedGames?size} of your games:<br/>
                <#list game.linkedGames as linkedGame>
                  ${linkedGame.name}<#if linkedGame.myRating??> (${linkedGame.myRating}/10)</#if><#if linkedGame_has_next>, </#if>
                </#list>
              </div>

          </div>

        </div>
      </#list>
  </div>
</main>

<script>
const games = document.querySelectorAll('.game-container');
const searchInput = document.getElementById('searchInput');
const minRatingFilter = document.getElementById('minRatingFilter');
const minComplexityFilter = document.getElementById('minComplexityFilter');
const maxComplexityFilter = document.getElementById('maxComplexityFilter');
const maxTimeFilter = document.getElementById('maxTimeFilter');
const sortSelect = document.getElementById('sortSelect');
const visibleCount = document.getElementById('visibleCount');

function updateVisibleCount() {
  const visible = Array.from(games).filter(g => !g.classList.contains('hidden')).length;
  visibleCount.textContent = visible;
}

function filterAndSort() {
  const searchTerm = searchInput.value.toLowerCase();
  const minRating = parseFloat(minRatingFilter.value) || 0;
  const minComplexity = parseFloat(minComplexityFilter.value) || 0;
  const maxComplexity = parseFloat(maxComplexityFilter.value) || 999;
  const maxTime = parseInt(maxTimeFilter.value) || 999999;

  const gamesArray = Array.from(games);

  gamesArray.forEach(game => {
    const name = game.dataset.name;
    const ratingStr = game.dataset.rating;
    const complexityStr = game.dataset.complexity;
    const timeStr = game.dataset.time;

    const rating = ratingStr ? parseFloat(ratingStr) : null;
    const complexity = complexityStr ? parseFloat(complexityStr) : null;
    const time = timeStr ? parseInt(timeStr) : null;

    let show = true;

    if (searchTerm && !name.includes(searchTerm)) {
      show = false;
    }

    if (minRating > 0 && rating !== null && rating < minRating) {
      show = false;
    }

    if (minComplexity > 0 && complexity !== null && complexity < minComplexity) {
      show = false;
    }

    if (maxComplexity < 999 && complexity !== null && complexity > maxComplexity) {
      show = false;
    }

    if (maxTime < 999999 && time !== null && time > maxTime) {
      show = false;
    }

    if (show) {
      game.classList.remove('hidden');
    } else {
      game.classList.add('hidden');
    }
  });

  const visibleGames = gamesArray.filter(g => !g.classList.contains('hidden'));
  const sortValue = sortSelect.value;

  visibleGames.sort((a, b) => {
    switch(sortValue) {
      case 'score-desc':
        return parseFloat(b.dataset.score) - parseFloat(a.dataset.score);
      case 'rating-desc': {
        const aRating = a.dataset.rating ? parseFloat(a.dataset.rating) : null;
        const bRating = b.dataset.rating ? parseFloat(b.dataset.rating) : null;
        if (aRating === null && bRating === null) return 0;
        if (aRating === null) return 1;
        if (bRating === null) return -1;
        return bRating - aRating;
      }
      case 'complexity-asc': {
        const aComp = a.dataset.complexity ? parseFloat(a.dataset.complexity) : null;
        const bComp = b.dataset.complexity ? parseFloat(b.dataset.complexity) : null;
        if (aComp === null && bComp === null) return 0;
        if (aComp === null) return 1;
        if (bComp === null) return -1;
        return aComp - bComp;
      }
      case 'complexity-desc': {
        const aComp = a.dataset.complexity ? parseFloat(a.dataset.complexity) : null;
        const bComp = b.dataset.complexity ? parseFloat(b.dataset.complexity) : null;
        if (aComp === null && bComp === null) return 0;
        if (aComp === null) return 1;
        if (bComp === null) return -1;
        return bComp - aComp;
      }
      case 'time-asc': {
        const aTime = a.dataset.time ? parseInt(a.dataset.time) : null;
        const bTime = b.dataset.time ? parseInt(b.dataset.time) : null;
        if (aTime === null && bTime === null) return 0;
        if (aTime === null) return 1;
        if (bTime === null) return -1;
        return aTime - bTime;
      }
      case 'name-asc':
        return a.dataset.name.localeCompare(b.dataset.name);
      default:
        return 0;
    }
  });

  const container = games[0].parentElement;
  visibleGames.forEach(game => container.appendChild(game));

  updateVisibleCount();
}

searchInput.addEventListener('input', filterAndSort);
minRatingFilter.addEventListener('input', filterAndSort);
minComplexityFilter.addEventListener('input', filterAndSort);
maxComplexityFilter.addEventListener('input', filterAndSort);
maxTimeFilter.addEventListener('input', filterAndSort);
sortSelect.addEventListener('change', filterAndSort);

filterAndSort();
</script>

</body>
</html>
