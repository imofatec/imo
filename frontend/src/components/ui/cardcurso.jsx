import EstrelaIcon from './estrelaicon'
import fotoCurso from '@/assets/thumb.jpg'
import React from 'react';

export default function CardCurso({ nomeCurso, notaCurso, avaliacoesCurso }) {
    {/*
    Implementação do modal nos cards
    */}
    return <>
        <div className="flex flex-col text-center max-w-64 font-semibold px-5 cursor-pointer mb-12 hover:bg-slate-300  text-white">
            <img className="max-w-thumb max-h-thumb px-5" src={fotoCurso}></img>
            <h6 className="">
                {nomeCurso}
            </h6>
            <div className="flex flex-row text-center justify-around ">
                <p className=''>
                    {/* 
            nota média do curso 
            */}
                    {notaCurso}
                </p>
                {/* 
            > Implementar logica das estrelas baseado noa avaliação
            > Escolha estética de estrelas com a equipe
            */}
                <EstrelaIcon></EstrelaIcon>
                <EstrelaIcon></EstrelaIcon>
                <EstrelaIcon></EstrelaIcon>
                <EstrelaIcon></EstrelaIcon>
                <EstrelaIcon desativado={true}></EstrelaIcon>
                <p className='text-gray-400'>
                    {/* 
            quantidade de avaliações
            */}
                    ({avaliacoesCurso})
                </p>
            </div>

        </div>
    </>
}