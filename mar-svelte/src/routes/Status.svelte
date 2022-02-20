
<script>
	import { onMount } from 'svelte';
    let url = MAR.toStatusURL();
	
    let analysisModels = undefined;
	onMount(async () => {
		const res = await fetch(url);
		const status = await res.json();
        analysisModels = status.analysisModels
	});
</script>

<style>
    h4, p, li {
        color: whitesmoke;        
    }

    h4 {
        font-weight: bold;
    }
</style>

<h4>Stats</h4>
{#if analysisModels == undefined }
	<p>Loading...</p>
{:else if analysisModels.length == 0}
    <p>There is something weird going on... No analysisModels found :-(</p>
{:else}
    <ul>
    {#each analysisModels as m}
        <li>{m.name} - {m.count}</li>
    {/each}
    </ul>    
{/if}

<h4>Raw analysisModels</h4>
<p>
    The following packages contain the raw analysisModels crawled by MAR. It should be ok to use these analysisModels for research purposes (and even better if you cite our MAR papers!).
    You can also checkout <a href="http://modelset.github.io">ModelSet</a> which is a curated and labelled dataset of analysisModels.
</p>
<ul>    
    <li><a href="http://sanchezcuadrado.es/files/raw/repo-github-ecore.tar.gz">Ecore - GitHub</a></li>
    <li><a href="http://sanchezcuadrado.es/files/raw/repo-atlanmod-ecore.tar.gz">Ecore - Atlanmod</a></li>
    <li><a href="http://sanchezcuadrado.es/files/raw/repo-genmymodel-ecore.tar.gz">Ecore - GenMyModel</a></li>
    <li><a href="http://sanchezcuadrado.es/files/raw/repo-genmymodel-archimate.tar.gz">Archimate - GenMyModel</a></li>
    <li><a href="http://sanchezcuadrado.es/files/raw/repo-github-archimate.tar.gz">Archimate - GitHub</a></li>
    <li><a href="http://sanchezcuadrado.es/files/raw/repo-genmymodel-bpmn2.tar.gz">BPMN2 - GenMyModel</a></li>
    <li><a href="http://sanchezcuadrado.es/files/raw/repo-genmymodel-rds.tar.gz">RDS - GenMyModel</a></li>
    <li><a href="http://sanchezcuadrado.es/files/raw/repo-genmymodel-uml.tar.gz">UML - GenMyModel</a></li>
    <li><a href="http://sanchezcuadrado.es/files/raw/repo-github-pnml.tar.gz">PNML - GitHub</a></li>
    <li><a href="http://sanchezcuadrado.es/files/raw/repo-github-sculptor.tar.gz">Sculptor - GitHub</a></li>
    <li><a href="http://sanchezcuadrado.es/files/raw/repo-github-uml.tar.gz">UML - GitHub</a></li>
    <li><a href="http://sanchezcuadrado.es/files/raw/repo-github-xtext.tar.gz">Xtext - GitHub</a></li>
    <!--
    <li><a href=" repo-simulink-emf.tar.gz">Simulink</a></li>
    -->
</ul>
