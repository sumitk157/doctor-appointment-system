document.addEventListener("DOMContentLoaded", () => {
    // Stagger float-in animations
    const cards = document.querySelectorAll(".float-in");
    cards.forEach((card, index) => {
        card.style.animationDelay = `${index * 0.08}s`;
    });

    // Language toggle
    const langToggleButtons = document.querySelectorAll("#langToggle");
    let currentLang = localStorage.getItem("doctorAppLang") || "en";

    function applyLang(lang) {
        const elems = document.querySelectorAll("[data-en][data-hi]");
        elems.forEach(el => {
            const text = lang === "hi" ? el.getAttribute("data-hi") : el.getAttribute("data-en");
            if (text) {
                el.textContent = text;
            }
        });
        langToggleButtons.forEach(btn => btn.setAttribute("data-lang", lang));
        localStorage.setItem("doctorAppLang", lang);
    }

    langToggleButtons.forEach(btn => {
        btn.addEventListener("click", () => {
            currentLang = currentLang === "en" ? "hi" : "en";
            applyLang(currentLang);
        });
    });

    applyLang(currentLang);
});


// Webcam face capture logic
let currentPatientId = null;
let videoStream = null;

window.openFaceCaptureModal = function (button) {
    const id = button.getAttribute("data-patient-id");
    currentPatientId = id;
    const modal = document.getElementById("faceCaptureModal");
    const video = document.getElementById("faceVideo");
    const canvas = document.getElementById("faceCanvas");

    if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
        alert("Webcam not supported in this browser.");
        return;
    }

    modal.classList.remove("hidden");
    canvas.classList.add("hidden");
    video.classList.remove("hidden");

    navigator.mediaDevices.getUserMedia({ video: true, audio: false })
        .then(stream => {
            videoStream = stream;
            video.srcObject = stream;
        })
        .catch(err => {
            console.error("Error accessing camera", err);
            alert("Cannot access camera. Please allow camera permission.");
        });
};

window.closeFaceCaptureModal = function () {
    const modal = document.getElementById("faceCaptureModal");
    modal.classList.add("hidden");
    const video = document.getElementById("faceVideo");
    if (videoStream) {
        videoStream.getTracks().forEach(t => t.stop());
        videoStream = null;
    }
    video.srcObject = null;
};

window.captureFaceFrame = function () {
    if (!currentPatientId) return;
    const video = document.getElementById("faceVideo");
    const canvas = document.getElementById("faceCanvas");
    const ctx = canvas.getContext("2d");

    canvas.width = video.videoWidth || 320;
    canvas.height = video.videoHeight || 240;

    ctx.drawImage(video, 0, 0, canvas.width, canvas.height);

    const dataUrl = canvas.toDataURL("image/png");
    const base64 = dataUrl.split(",")[1];

    document.getElementById("faceImageData").value = base64;

    const form = document.getElementById("faceUploadForm");
    form.action = `/patients/${currentPatientId}/face`;
    form.submit();

    closeFaceCaptureModal();
};

// Edit patient modal
window.openEditPatientModal = function(btn) {
    const id = btn.getAttribute("data-id");
    const name = btn.getAttribute("data-name");
    const phone = btn.getAttribute("data-phone");
    const gender = btn.getAttribute("data-gender");

    document.getElementById("editName").value = name;
    document.getElementById("editPhone").value = phone;
    document.getElementById("editGender").value = gender;

    const form = document.getElementById("editPatientForm");
    form.action = `/patients/edit/${id}`;

    document.getElementById("editPatientModal").classList.remove("hidden");
};

window.closeEditPatientModal = function() {
    document.getElementById("editPatientModal").classList.add("hidden");
};

// Edit appointment modal
window.openEditAppointmentModal = function(btn) {
    const id = btn.getAttribute("data-id");
    const date = btn.getAttribute("data-date");
    const time = btn.getAttribute("data-time");

    document.getElementById("editAppDate").value = date;
    document.getElementById("editAppTime").value = time;

    const form = document.getElementById("editAppointmentForm");
    form.action = `/appointments/edit/${id}`;

    document.getElementById("editAppointmentModal").classList.remove("hidden");
};

window.closeEditAppointmentModal = function() {
    document.getElementById("editAppointmentModal").classList.add("hidden");
};
