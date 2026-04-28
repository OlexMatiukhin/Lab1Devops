{{- define "microservices5.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{- define "microservices5.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name (include "microservices5.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end }}

{{- define "microservices5.namespace" -}}
{{- default .Release.Namespace .Values.namespace -}}
{{- end }}

{{- define "microservices5.labels" -}}
app.kubernetes.io/name: {{ include "microservices5.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
helm.sh/chart: {{ printf "%s-%s" .Chart.Name .Chart.Version | quote }}
{{- end }}

{{- define "microservices5.selectorLabels" -}}
app.kubernetes.io/name: {{ include "microservices5.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{- define "microservices5.componentLabels" -}}
{{- $component := required "component is required" .component -}}
app: {{ $component }}
{{- end }}
