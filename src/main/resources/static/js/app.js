let stompClient = null;
const statusIcon = document.getElementById('ws-icon');
const cLevel = document.getElementById('c-level');
const cInfo = document.getElementById('c-info');
const footerLeft = document.getElementById('footer-left');
const logFooterMeta = document.getElementById('logFooter');
const logFooter = (logFooterMeta && (logFooterMeta.content === 'true' || logFooterMeta.content === 'True'));
const isRedacteur = !!document.getElementById('role-redacteur');

function setWsConnected(on) {
    if (statusIcon) {
        statusIcon.textContent = on ? '🔗' : '⛓️‍💥';
        statusIcon.title = on ? 'Connecté' : 'Déconnecté';
    }
}

function toggleSecuredTabs(level) {
    if (!isRedacteur) return;
    const liReu = document.getElementById('li-reunion');
    const paneReu = document.getElementById('tab-reunion');
    const liPar = document.getElementById('li-partie');
    const panePar = document.getElementById('tab-partie');

    const showReu = (level === 1);
    const showPar = (level === 2);

    if (liReu) liReu.classList.toggle('d-none', !showReu);
    if (paneReu) paneReu.classList.toggle('d-none', !showReu);
    if (liPar) liPar.classList.toggle('d-none', !showPar);
    if (panePar) panePar.classList.toggle('d-none', !showPar);

    const active = document.querySelector('.tab-pane.active.show');
    if (active && active.classList.contains('d-none')) {
        const publicTabTrigger = document.querySelector('#public-tab');
        if (publicTabTrigger) new bootstrap.Tab(publicTabTrigger).show();
    }
}

function updateInfo(level, dto) {
    if (!cInfo) return;
    const baseClass = 'form-control text-start';
    let text = '';
    let cls = baseClass;
    if (Number(level) === 0) {
        text = 'reunion en erreur';
        cls = baseClass + ' text-danger';
    } else if (Number(level) === 1) {
        text = 'En attente de joueurs';
    } else {
        const nb = dto && dto.reunion && Array.isArray(dto.reunion.listeJoueurs)
            ? dto.reunion.listeJoueurs.length
            : 0;
        text = `${nb} joueurs`;
    }
    cInfo.className = cls;
    cInfo.textContent = text;
}

function statusText(level) {
    switch (Number(level)) {
        case 0:
            return 'Erreur';
        case 1:
            return 'En attente de joueurs';
        case 2:
            return 'En cours';
        case 3:
            return 'Terminée';
        default:
            return 'Inconnu';
    }
}

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = () => {
    };

    stompClient.connect({}, function () {
        setWsConnected(true);
        stompClient.subscribe('/topic/messages', function (frame) {
            const dto = JSON.parse(frame.body);
            if (cLevel) cLevel.textContent = dto.level;
            updateInfo(dto.level, dto);
            toggleSecuredTabs(dto.level);
            if (logFooter && footerLeft) footerLeft.textContent = `MessageDto → level=${dto.level}`;
        });
    }, function () {
        setWsConnected(false);
        setTimeout(connect, 2000);
    });
}

document.addEventListener('DOMContentLoaded', function () {
    const initialLevel = parseInt(cLevel ? cLevel.textContent : '0', 10) || 0;
    toggleSecuredTabs(initialLevel);
    connect();
});
