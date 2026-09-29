const API = "";


// =========================
// NAVIGATION
// =========================

function showSection(sectionId) {

    const sections = document.querySelectorAll(".section");

    sections.forEach(section => {
        section.classList.remove("active");
    });

    document.getElementById(sectionId).classList.add("active");

    if (sectionId === "dashboard") {
        loadDashboard();
    }

    if (sectionId === "donors") {
        loadDonors();
    }

    if (sectionId === "inventory") {
        loadInventory();
    }
}


// =========================
// DASHBOARD
// =========================

async function loadDashboard() {

    try {

        const donorResponse =
            await fetch(`${API}/api/donors`);

        const donors =
            await donorResponse.json();

        document.getElementById("totalDonors").textContent =
            donors.length;


        const inventoryResponse =
            await fetch(`${API}/api/blood-units/inventory`);

        const inventory =
            await inventoryResponse.json();


        let totalUnits = 0;

        Object.values(inventory).forEach(count => {
            totalUnits += count;
        });

        document.getElementById("totalUnits").textContent =
            totalUnits;


        displayInventory(
            inventory,
            "dashboardInventory"
        );

    } catch (error) {

        console.error(
            "Dashboard error:",
            error
        );
    }
}


// =========================
// DONORS
// =========================

async function addDonor() {

    const donor = {

        name:
            document.getElementById("donorName").value.trim(),

        age:
            Number(
                document.getElementById("donorAge").value
            ),

        bloodGroup:
            document
                .getElementById("donorBloodGroup")
                .value
                .trim()
                .toUpperCase(),

        phone:
            document
                .getElementById("donorPhone")
                .value
                .trim()
    };


    if (
        !donor.name ||
        !donor.age ||
        !donor.bloodGroup ||
        !donor.phone
    ) {

        alert("Please fill in all donor details.");

        return;
    }


    try {

        const response =
            await fetch(`${API}/api/donors`, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(donor)
            });


        if (response.ok) {

            alert("Donor added successfully.");

            clearDonorForm();

            loadDonors();

            loadDashboard();

        } else {

            const error =
                await response.json();

            showError(error);
        }

    } catch (error) {

        alert(
            "Unable to connect to the server."
        );

        console.error(error);
    }
}


async function loadDonors() {

    try {

        const response =
            await fetch(`${API}/api/donors`);

        const donors =
            await response.json();


        const table =
            document.getElementById("donorTable");

        table.innerHTML = "";


        if (donors.length === 0) {

            table.innerHTML = `
                <tr>
                    <td colspan="6">
                        No donors found.
                    </td>
                </tr>
            `;

            return;
        }


        donors.forEach(donor => {

            const row =
                document.createElement("tr");


            row.innerHTML = `

                <td>${donor.id}</td>

                <td>${donor.name}</td>

                <td>${donor.age}</td>

                <td>${donor.bloodGroup}</td>

                <td>${donor.phone}</td>

                <td>

                    <button
                        class="delete-btn"
                        onclick="deleteDonor(${donor.id})">

                        Delete

                    </button>

                </td>
            `;


            table.appendChild(row);
        });

    } catch (error) {

        console.error(
            "Unable to load donors:",
            error
        );
    }
}


async function deleteDonor(id) {

    const confirmed =
        confirm(
            "Are you sure you want to delete this donor?"
        );


    if (!confirmed) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API}/api/donors/${id}`,
                {
                    method: "DELETE"
                }
            );


        if (response.ok) {

            alert(
                "Donor deleted successfully."
            );

            loadDonors();

            loadDashboard();

        } else {

            alert(
                "Unable to delete donor."
            );
        }

    } catch (error) {

        alert(
            "Unable to connect to the server."
        );

        console.error(error);
    }
}


function clearDonorForm() {

    document.getElementById(
        "donorName"
    ).value = "";

    document.getElementById(
        "donorAge"
    ).value = "";

    document.getElementById(
        "donorBloodGroup"
    ).value = "";

    document.getElementById(
        "donorPhone"
    ).value = "";
}


// =========================
// DONATIONS
// =========================

async function addDonation() {

    const donorId =
        Number(
            document.getElementById(
                "donationDonorId"
            ).value
        );


    const donationDate =
        document.getElementById(
            "donationDate"
        ).value;


    const quantity =
        Number(
            document.getElementById(
                "donationQuantity"
            ).value
        );


    if (
        !donorId ||
        !donationDate ||
        !quantity
    ) {

        alert(
            "Please fill in all donation details."
        );

        return;
    }


    const donation = {

        donor: {
            id: donorId
        },

        donationDate: donationDate,

        quantity: quantity
    };


    try {

        const response =
            await fetch(
                `${API}/api/donations`,
                {

                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body:
                        JSON.stringify(donation)
                }
            );


        const message =
            document.getElementById(
                "donationMessage"
            );


        if (response.ok) {

            message.textContent =
                "Donation recorded successfully.";

            message.style.color =
                "green";


            document.getElementById(
                "donationDonorId"
            ).value = "";

            document.getElementById(
                "donationDate"
            ).value = "";

            document.getElementById(
                "donationQuantity"
            ).value = "";


            loadDashboard();

            loadInventory();

        } else {

            const error =
                await response.json();

            message.textContent =
                JSON.stringify(error);

            message.style.color =
                "red";
        }

    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to the server."
        );
    }
}


// =========================
// INVENTORY
// =========================

async function loadInventory() {

    try {

        const response =
            await fetch(
                `${API}/api/blood-units/inventory`
            );


        const inventory =
            await response.json();


        displayInventory(
            inventory,
            "inventoryContainer"
        );


    } catch (error) {

        console.error(
            "Inventory error:",
            error
        );
    }
}


function displayInventory(
    inventory,
    containerId
) {

    const container =
        document.getElementById(
            containerId
        );


    container.innerHTML = "";


    Object.entries(inventory).forEach(
        ([bloodGroup, count]) => {

            const card =
                document.createElement("div");

            card.className =
                "inventory-card";


            card.innerHTML = `

                <h3>${bloodGroup}</h3>

                <p>${count}</p>

                <span>
                    Available Units
                </span>

            `;


            container.appendChild(card);
        }
    );
}


// =========================
// ISSUE BLOOD
// =========================

async function issueBlood() {

    const issueRecord = {

        bloodGroup:
            document
                .getElementById(
                    "issueBloodGroup"
                )
                .value
                .trim()
                .toUpperCase(),

        quantity:
            Number(
                document.getElementById(
                    "issueQuantity"
                ).value
            ),

        issuedTo:
            document
                .getElementById(
                    "issuedTo"
                )
                .value
                .trim(),

        issueDate:
            document.getElementById(
                "issueDate"
            ).value
    };


    if (
        !issueRecord.bloodGroup ||
        !issueRecord.quantity ||
        !issueRecord.issuedTo ||
        !issueRecord.issueDate
    ) {

        alert(
            "Please fill in all issue details."
        );

        return;
    }


    try {

        const response =
            await fetch(
                `${API}/api/issues/issue`,
                {

                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body:
                        JSON.stringify(issueRecord)
                }
            );


        const message =
            document.getElementById(
                "issueMessage"
            );


        if (response.ok) {

            message.textContent =
                "Blood issued successfully.";

            message.style.color =
                "green";


            document.getElementById(
                "issueBloodGroup"
            ).value = "";

            document.getElementById(
                "issueQuantity"
            ).value = "";

            document.getElementById(
                "issuedTo"
            ).value = "";

            document.getElementById(
                "issueDate"
            ).value = "";


            loadInventory();

            loadDashboard();

        } else {

            const error =
                await response.json();

            message.textContent =
                JSON.stringify(error);

            message.style.color =
                "red";
        }

    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to the server."
        );
    }
}


// =========================
// ERROR DISPLAY
// =========================

function showError(error) {

    if (typeof error === "object") {

        const messages =
            Object.values(error);

        alert(
            messages.join("\n")
        );

    } else {

        alert(
            "An error occurred."
        );
    }
}


// =========================
// INITIAL LOAD
// =========================

loadDashboard();