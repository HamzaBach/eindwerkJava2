/* Navigation bar component */
const template = document.createElement('template');

template.innerHTML = `
  <nav class="navbar navbar-expand-md bg-dark navbar-dark py-3">
      <div class="container">
          <a href="#" class="navbar-brand">Eindproject Java 2</a>

          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navmenu">
              <span class="navbar-toggler-icon"></span>
          </button>

          <div class="collapse navbar-collapse" id="navmenu">
              <ul class="navbar-nav ms-auto">

                  <li class="nav-item">
                      <a th:href="#" class="nav-link">Home</a>
                  </li>
                  <li class="nav-item">
                      <a href="#" class="nav-link">Articles</a>
                  </li>
                  <li class="nav-item">
                      <a href="#" class="nav-link">Inventory</a>
                  </li>
                  <li class="nav-item">
                      <a href="#" class="nav-link">Suppliers</a>
                  </li>
                  <li class="nav-item">
                      <a href="#" class="nav-link">Warehouses</a>
                  </li>
                  <li class="nav-item">
                      <a href="#" class="nav-link">Employees</a>
                  </li>
                  <li class="nav-item">
                      <a href="#" class="nav-link">Traffic</a>
                  </li>
                  <li class="nav-item">
                      <a href="#" class="nav-link">Settings</a>
                  </li>
              </ul>
          </div>
      </div>
  </nav>
`;

document.body.appendChild(template.content);