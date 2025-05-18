 const selectClass = document.getElementById('classificacao');
    const container   = document.getElementById('specific-container');

    async function loadSection() {
      const val = selectClass.value;
      if (!val) {
        container.innerHTML = '';
        return;
      }
      try {
        const res = await fetch(`/fragments/${val}`);
        if (!res.ok) throw new Error(res.status);
        container.innerHTML = await res.text();
          console.log("foi")
      } catch (e) {
        console.error('Erro ao carregar fragmento:', e);
        container.innerHTML = '<p>Não foi possível carregar os campos.</p>';
      }
    }

    selectClass.addEventListener('change', loadSection);
    // Se já houver um valor selecionado ao abrir a página:
    loadSection();