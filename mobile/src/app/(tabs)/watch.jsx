import React, { useState, useMemo } from "react";
import { View, Text, ScrollView, Pressable, Alert } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import PlayerHeader from "../../components/watch/playerHeader";
import LessonList from "../../components/watch/lessonList";
import CertificateButton from "../../components/watch/certificateButton";

export default function Watch() {
  const lessons = [
    { id: "l1", title: "Introdução ao Curso", description: "Visão geral do curso.", youtubeId: "8vm3Tkv43jw" },
    { id: "l2", title: "Conceitos Fundamentais", description: "Fundamentos essenciais.", youtubeId: "7QU1nvuxaMA" },
    { id: "l3", title: "Prática e Exercícios", description: "Aplicação prática.", youtubeId: "8vm3Tkv43jw" },
    { id: "l4", title: "Aprofundamento", description: "Tópicos avançados.", youtubeId: "dQw4w9WgXcQ" },
  ];

  const [currentLesson, setCurrentLesson] = useState(lessons[0]);
  const [watched, setWatched] = useState(new Set());

  const watchedCount = watched.size;
  const allWatched = watchedCount === lessons.length;

  const handleToggleWatched = (lessonId) => {
    setWatched((prev) => {
      const next = new Set(prev);
      if (next.has(lessonId)) next.delete(lessonId);
      else next.add(lessonId);
      return next;
    });
  };

  const handleGenerateCertificate = () => {
    if (!allWatched) {
      Alert.alert("Atenção", "Você precisa assistir todas as aulas para gerar o certificado.");
      return;
    }
    Alert.alert("Sucesso", "Certificado gerado.");
  };

  const progressPercent = useMemo(() => {
    return Math.round((watchedCount / lessons.length) * 100);
  }, [watchedCount, lessons.length]);

  return (
    <View className="flex-1 bg-custom-primary">
      <View className="px-6 pt-10 pb-4 flex-row items-center justify-between">
        <View className="flex-row items-center">
          <Pressable onPress={() => console.log("voltar")} className="mr-3">
            <Ionicons name="arrow-back-outline" size={26} color="white" />
          </Pressable>
          <Text className="text-white text-3xl font-bold">Assistir Aula</Text>
        </View>
        <Ionicons name="play-circle-outline" size={32} color="white" />
      </View>

      <ScrollView className="flex-1 px-4">
        <PlayerHeader
          youtubeId={currentLesson.youtubeId}
          title={currentLesson.title}
          description={currentLesson.description}
        />
        <View className="mt-2 px-2">
          <Text className="text-white font-medium mb-2">Progresso: {watchedCount} / {lessons.length}</Text>
          <View className="w-full h-3 bg-white/10 rounded-full overflow-hidden">
            <View className="h-full bg-white" style={{ width: `${progressPercent}%` }} />
          </View>
        </View>

        <View className="mt-6">
          <Text className="text-white text-xl font-bold mb-3">Aulas do Curso</Text>
          <LessonList
            lessons={lessons}
            currentId={currentLesson.id}
            onSelect={(lesson) => setCurrentLesson(lesson)}
            watchedSet={watched}
            onToggleWatched={handleToggleWatched}
          />
        </View>

        <View className="px-2 mt-6">
          <CertificateButton disabled={!allWatched} onPress={handleGenerateCertificate} />
        </View>
      </ScrollView>
    </View>
  );
}
