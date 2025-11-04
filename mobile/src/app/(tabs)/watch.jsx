import React, { useState, useMemo, useRef } from "react";
import { View, Text, ScrollView, Pressable, Alert, KeyboardAvoidingView, Platform } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import PlayerHeader from "../../components/watch/playerHeader";
import LessonList from "../../components/watch/lessonList";
import CertificateButton from "../../components/watch/certificateButton";
import CommentsList from "../../components/watch/commentList";
import CommentsPreview from "../../components/watch/commentPreview";
import CommentInput from "../../components/watch/commentInput";

export default function Watch() {
  const scrollRef = useRef(null);
  const [commentInputY, setCommentInputY] = useState(0);

  const scrollToComment = () => {
    requestAnimationFrame(() => {
      scrollRef.current?.scrollTo({
        y: Math.max(0, commentInputY - 80),
        animated: true,
      });
    });
  };
  const lessons = [
    { id: "l1", title: "Introdução ao Curso", description: "Visão geral do curso.", youtubeId: "8vm3Tkv43jw" },
    { id: "l2", title: "Conceitos Fundamentais", description: "Fundamentos essenciais.", youtubeId: "7QU1nvuxaMA" },
    { id: "l3", title: "Prática e Exercícios", description: "Aplicação prática.", youtubeId: "8vm3Tkv43jw" },
    { id: "l4", title: "Aprofundamento", description: "Tópicos avançados.", youtubeId: "fWvKvOViM3g" },
  ];

  const initialCommentsMap = {
    l1: [
      { id: "c1", authorName: "Vastobode", text: "Ótima aula, muito obrigado!! 😍" },
      { id: "c2", authorName: "Danielzinho", text: "Gostei muito, mas nao entendi direito... nao sobra nada pro betinha" },
    ],
    l2: [],
    l3: [{ id: "c3", authorName: "Montemor", text: "A explicação ficou massa 👏" }],
    l4: [],
  };
  const [commentsMap, setCommentsMap] = useState(initialCommentsMap);

  const [currentLesson, setCurrentLesson] = useState(lessons[0]);
  const [watched, setWatched] = useState(new Set());
  const [tab, setTab] = useState("lessons");

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

  const currentComments = commentsMap[currentLesson.id] ?? [];
  const latest = currentComments[0] || null;

  return (
    <KeyboardAvoidingView
      className="flex-1 bg-custom-primary"
      behavior={Platform.OS === "android" ? "padding" : "position"}
      keyboardVerticalOffset={Platform.OS === "android" ? 80 : -20}
    >
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

        <ScrollView
          ref={scrollRef}
          className="flex-1 px-4"
          keyboardShouldPersistTaps="handled"
          showsVerticalScrollIndicator={false}
        >
          <PlayerHeader
            youtubeId={currentLesson.youtubeId}
            title={currentLesson.title}
            description={currentLesson.description}
          />

          <View className="mt-2 px-2 flex-row gap-2">
            <Pressable
              onPress={() => setTab("lessons")}
              className={`px-4 py-2 rounded-full ${tab === "lessons" ? "bg-white" : "bg-white/10"}`}
            >
              <Text className={`${tab === "lessons" ? "text-black" : "text-white"}`}>Aulas</Text>
            </Pressable>

            <Pressable
              onPress={() => setTab("comments")}
              className={`px-4 py-2 rounded-full flex-row items-center gap-2 ${tab === "comments" ? "bg-white" : "bg-white/10"}`}
            >
              <Text className={`${tab === "comments" ? "text-black" : "text-white"}`}>Comentários</Text>
              <View className={`px-2 py-0.5 rounded-full ${tab === "comments" ? "bg-black/10" : "bg-white/20"}`}>
                <Text className={`${tab === "comments" ? "text-black/80" : "text-white/90"} text-xs`}>{currentComments.length}</Text>
              </View>
            </Pressable>
          </View>

          <CommentsPreview
            latest={latest}
            count={currentComments.length}
            onPress={() => setTab("comments")}
          />

          {tab === "lessons" && (
            <View className="mt-4 px-2">
              <Text className="text-white font-medium mb-2">
                Progresso: {watchedCount} / {lessons.length}
              </Text>
              <View className="w-full h-3 bg-white/10 rounded-full overflow-hidden">
                <View className="h-full bg-white" style={{ width: `${progressPercent}%` }} />
              </View>
            </View>
          )}

          <View className="mt-6">
            {tab === "lessons" ? (
              <>
                <Text className="text-white text-xl font-bold mb-3">Aulas do Curso</Text>
                <LessonList
                  lessons={lessons}
                  currentId={currentLesson.id}
                  onSelect={(lesson) => {
                    setCurrentLesson(lesson);
                    setTab("lessons");
                  }}
                  watchedSet={watched}
                  onToggleWatched={handleToggleWatched}
                />
              </>
            ) : (
              <>
                <Text className="text-white text-xl font-bold mb-3">Comentários</Text>
                <CommentsList comments={currentComments} />
              </>
            )}
          </View>

          {tab === "lessons" && (
            <View className="px-2 mt-6 mb-8">
              <CertificateButton disabled={!allWatched} onPress={handleGenerateCertificate} />
            </View>
          )}

          {tab === "comments" && (
            <View onLayout={(e) => setCommentInputY(e.nativeEvent.layout.y)}>
              <CommentInput
                onSubmit={async (text) => {
                  setCommentsMap((prev) => ({
                    ...prev,
                    [currentLesson.id]: [
                      {
                        id: String(Date.now()),
                        authorName: "Você",
                        text,
                      },
                      ...(prev[currentLesson.id] || []),
                    ],
                  }));
                }}
                onFocusInput={scrollToComment}
                minLength={2}
                maxLength={500}
              />
            </View>
          )}

        </ScrollView>
      </View>
    </KeyboardAvoidingView>
  );
}